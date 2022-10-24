package fr.playcards.cardgame;

import fr.playcards.PlayCardsApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KoiKoiWarsFrame {

    private String gameTitle;
    public KoiKoiWarsFrame(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(KoiKoiWarsFrame.class.getResource("koikoiwars-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(gameTitle);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}
