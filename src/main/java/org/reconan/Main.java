package org.reconan;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.reconan.ui.Banner;
import org.reconan.ui.SplashScreen;
import org.reconan.database.DatabaseConnection;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        setupStage(stage);
        Banner.print();
        DatabaseConnection.checkConnection();
        SplashScreen.show(stage);
    }

    private void setupStage(Stage stage) {
        stage.setTitle("ReConan");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
    }


    public static void main(String[] args) {
        launch();
    }
}