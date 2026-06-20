package org.reconan.database;

import org.reconan.config.DatabaseConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Singleton manager to handle database initialization and schema validation.
 */
public class DatabaseManager {
    private static DatabaseManager instance;

    private DatabaseManager() {
        // Private constructor for Singleton
    }

    /**
     * Retrieves the thread-safe singleton instance of DatabaseManager.
     * @return The DatabaseManager instance
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Helper to verify if the configured database is a custom user database and not a system database or empty.
     */
    private boolean isDatabaseCustomAndValid() {
        String url = DatabaseConfig.URL;
        if (url == null || url.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile("(?i)(databaseName|database)=([^;]+)");
        Matcher matcher = pattern.matcher(url);

        if (!matcher.find()) {
            return false;
        }

        String dbName = matcher.group(2);
        if (dbName == null || dbName.trim().isEmpty()) {
            return false;
        }

        String lowerDbName = dbName.trim().toLowerCase();
        if (lowerDbName.equals("master") || lowerDbName.equals("tempdb") || 
            lowerDbName.equals("model") || lowerDbName.equals("msdb")) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the database specified in the configuration exists, and creates it if it doesn't.
     * 
     * @throws SQLException if a database access error occurs
     */
    public void ensureDatabaseExists() throws SQLException {
        if (!isDatabaseCustomAndValid()) {
            System.out.println("SQL Server: Database URL is not configured with a valid custom database. Skipping creation check.");
            return;
        }

        String url = DatabaseConfig.URL;
        String user = DatabaseConfig.USER;
        String password = DatabaseConfig.PASSWORD;

        // Extract database name from URL using pattern matching
        Pattern pattern = Pattern.compile("(?i)(databaseName|database)=([^;]+)");
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        String dbName = matcher.group(2);
        String trimmedDbName = dbName.trim();

        // Remove the databaseName or database parameter from URL to connect to the server level
        String serverUrl = url.replaceAll("(?i)(databaseName|database)=[^;]+;?", "");

        System.out.println("SQL Server: Checking if database '" + trimmedDbName + "' exists...");

        Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Connect to server level
            conn = DriverManager.getConnection(serverUrl, user, password);

            // Check if the database exists in sys.databases
            String checkDbSql = "SELECT database_id FROM sys.databases WHERE name = ?";
            pstmt = conn.prepareStatement(checkDbSql);
            pstmt.setString(1, trimmedDbName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("SQL Server: Database '" + trimmedDbName + "' already exists.");
            } else {
                System.out.println("SQL Server: Database '" + trimmedDbName + "' does not exist. Creating it...");
                // Note: database names in CREATE DATABASE cannot be parameterized,
                // but since it comes from configuration, we execute it directly.
                // To be safe, we wrap it in square brackets.
                String createDbSql = "CREATE DATABASE [" + trimmedDbName + "]";
                stmt = conn.createStatement();
                stmt.executeUpdate(createDbSql);
                System.out.println("SQL Server: Database '" + trimmedDbName + "' created successfully.");
            }
        } finally {
            DatabaseConnection.close(rs, pstmt, stmt, conn);
        }
    }

    /**
     * Initializes the database schema by creating required tables if they do not exist.
     * 
     * @throws SQLException if a database access error occurs
     */
    public void initialize() throws SQLException {
        if (!isDatabaseCustomAndValid()) {
            System.err.println("SQL Server: Skipped schema initialization because the database is empty or a system database.");
            return;
        }

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            
            System.out.println("SQL Server: Initializing database schema...");

            // Read SQL script from resources
            String sqlScript = readSqlResource("/sql/init.sql");
            
            if (sqlScript != null && !sqlScript.isEmpty()) {
                // Execute the full script. SQL Server driver usually handles multiple batches if needed.
                // Note: For complex scripts with GO commands, we would need to split by GO.
                stmt.execute(sqlScript);
                System.out.println("SQL Server: Database schema initialized successfully.");
            } else {
                System.err.println("SQL Server: Failed to read schema initialization script.");
            }
        } finally {
            DatabaseConnection.close(stmt, conn);
        }
    }

    /**
     * Reads a SQL file from the resources directory.
     * 
     * @param path The path to the SQL file relative to resources
     * @return The content of the SQL file as a String
     */
    private String readSqlResource(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("SQL Server: Resource not found: " + path);
                return null;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            System.err.println("SQL Server: Error reading SQL resource " + path + ": " + e.getMessage());
            return null;
        }
    }
}
