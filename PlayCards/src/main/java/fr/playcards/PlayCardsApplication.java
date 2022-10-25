package fr.playcards;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;

public class PlayCardsApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PlayCardsApplication.class.getResource("playcards-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PlayCards");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void stop() {
        try {
            Naming.unbind("play-cards/1099/observablelist");
        } catch(Exception e) {
            System.out.println("erreur"+e);
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}