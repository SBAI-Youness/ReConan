package org.reconan.database;

import org.reconan.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD
        );
    }

    public static void checkConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("SQL Server: Connected to Database Successfully!");
            } else {
                System.out.println("SQL Server: Failed to connect to Database.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Server: Database Connection Error: " + e.getMessage());
        }
    }
}