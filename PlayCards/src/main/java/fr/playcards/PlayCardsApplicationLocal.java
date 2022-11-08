package fr.playcards;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;

public class PlayCardsApplicationLocal extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PlayCardsApplication.class.getResource("playcards-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("PlayCards");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            stage.setOnCloseRequest(e -> {
                Platform.exit();});
        } catch (Exception e) {
            System.out.println("PlayCardsApplication start method Error : "+e);
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}