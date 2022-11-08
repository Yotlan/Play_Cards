package fr.playcards;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PlayCardsApplication extends Application {

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
                fxmlLoader.setController(null);
                Platform.exit();
                System.exit(0);
            });
        } catch (Exception e) {
            System.out.println("PlayCardsApplication start method Error : "+e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}