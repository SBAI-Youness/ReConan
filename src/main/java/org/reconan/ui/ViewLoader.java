package org.reconan.ui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * Utility for loading FXML views and managing application scenes.
 */
public class ViewLoader {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Loads a view from FXML and sets it as the current scene.
     * @param fxmlPath Path to the FXML file relative to resources.
     * @param title Title for the stage.
     */
    public static void loadView(String fxmlPath, String title) {
        loadViewWithController(fxmlPath, title);
    }

    /**
     * Loads a view from FXML, sets it as the current scene, and returns the controller.
     * @param fxmlPath Path to the FXML file relative to resources.
     * @param title Title for the stage.
     * @param <T> The type of the controller.
     * @return The controller instance.
     */
    public static <T> T loadViewWithController(String fxmlPath, String title) {
        return loadViewIntoStage(primaryStage, fxmlPath, title, false);
    }

    /**
     * Loads a view from FXML into a specific stage.
     * @param stage The stage to load the view into.
     * @param fxmlPath Path to the FXML file relative to resources.
     * @param title Title for the stage.
     * @param transparent Whether the scene should have a transparent fill.
     * @param <T> The type of the controller.
     * @return The controller instance.
     */
    public static <T> T loadViewIntoStage(Stage stage, String fxmlPath, String title, boolean transparent) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ViewLoader.class.getResource(fxmlPath)));
            Parent root = loader.load();
            Scene scene = transparent ? new Scene(root, Color.TRANSPARENT) : new Scene(root);
            
            // Load global CSS
            String cssPath = "/css/styles.css";
            if (ViewLoader.class.getResource(cssPath) != null) {
                scene.getStylesheets().add(Objects.requireNonNull(ViewLoader.class.getResource(cssPath)).toExternalForm());
            }

            stage.setTitle(title);
            stage.setScene(scene);
            
            // Show the stage first so it has dimensions
            stage.show();
            
            // Re-center reaaally perfectly after show
            centerOnScreen(stage);
            
            return loader.getController();
        } catch (IOException e) {
            System.err.println("Error loading view: " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Perfectly centers a stage on the primary screen.
     * @param stage The stage to center.
     */
    private static void centerOnScreen(Stage stage) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX() + (bounds.getWidth() - stage.getWidth()) / 2.0;
        double y = bounds.getMinY() + (bounds.getHeight() - stage.getHeight()) / 2.0;
        stage.setX(x);
        stage.setY(y);
    }
}
