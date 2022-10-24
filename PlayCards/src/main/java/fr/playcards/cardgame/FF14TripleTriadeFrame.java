package fr.playcards.cardgame;

import fr.playcards.PlayCardsApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FF14TripleTriadeFrame {

    private String gameTitle;
    public FF14TripleTriadeFrame(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FF14TripleTriadeFrame.class.getResource("ff14tripletriade-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(gameTitle);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}
