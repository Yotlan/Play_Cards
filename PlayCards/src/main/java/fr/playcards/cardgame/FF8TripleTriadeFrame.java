package fr.playcards.cardgame;

import fr.playcards.PlayCardsApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FF8TripleTriadeFrame {

    private String gameTitle;
    public CardGame game;
    public FF8TripleTriadeFrame(String gameTitle, CardGame game) {
        this.gameTitle = gameTitle;
        this.game = game;
    }

    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FF8TripleTriadeFrame.class.getResource("ff8tripletriade-view.fxml"));
        fxmlLoader.setController(game.getController());
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(gameTitle);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}
