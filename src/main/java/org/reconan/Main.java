package org.reconan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.reconan.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("ReConan");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
        
        String statusText;
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                statusText = "Connected to Database Successfully!";
                System.out.println(statusText);
            } else {
                statusText = "Failed to connect to Database.";
                System.out.println(statusText);
            }
        } catch (SQLException e) {
            statusText = "Database Connection Error: " + e.getMessage();
            System.err.println(statusText);
        }

        stage.setScene(new Scene(new Label(statusText), 400, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}