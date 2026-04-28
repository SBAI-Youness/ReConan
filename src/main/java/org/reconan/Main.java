package org.reconan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Set your SQL Server Configuration (Database Name, Username, Password, ...)
        String url = "jdbc:sqlserver://localhost:1433;databaseName=TestDB;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "Azerty123@";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            System.out.println("Connected successfully!");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sys.tables");

            while (rs.next()) {
                System.out.println("Table: " + rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}