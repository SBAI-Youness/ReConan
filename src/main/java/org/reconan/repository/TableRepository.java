package org.reconan.repository;

import org.reconan.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Provides operations related to database tables.
 */
public class TableRepository {
    // Retrieve and print all table names from the database
    public void printAllTables() {
        String query = "SELECT name FROM sys.tables";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Iterate through result set and print table names
            while (rs.next()) {
                System.out.println("Table: " + rs.getString("name"));
            }
        } catch (Exception e) {
            // Handle query or connection errors
            e.printStackTrace();
        }
    }
}