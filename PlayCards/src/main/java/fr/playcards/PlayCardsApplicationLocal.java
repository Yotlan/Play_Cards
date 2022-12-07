package fr.playcards;

//Import part
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
@author Yotlan LE CROM

This class is same as PlayCardsApplication class.
 */

public class PlayCardsApplicationLocal extends Application {

    @Override
    public void start(Stage stage) {
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
        launch();
    }
}