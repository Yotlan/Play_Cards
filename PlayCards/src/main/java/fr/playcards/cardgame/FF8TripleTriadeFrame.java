package fr.playcards.cardgame;

import fr.playcards.PlayCardsApplication;
import fr.playcards.client.IClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class FF8TripleTriadeFrame {

    private String gameTitle;
    public CardGame game;
    public IClient client;
    public FF8TripleTriadeFrame(String gameTitle, CardGame game, IClient client) {
        this.gameTitle = gameTitle;
        this.game = game;
        this.client = client;
    }

    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FF8TripleTriadeFrame.class.getResource("ff8tripletriade-view.fxml"));
        fxmlLoader.setControllerFactory(clazz -> {
                    if (clazz == FF8TripleTriadeController.class) {
                        try {
                            FF8TripleTriadeController controller = new FF8TripleTriadeController(this.game.getUUID());
                            controller.setGame(this.game, this.client);
                            return controller;
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        // default behavior:
                        try {
                            return clazz.newInstance();
                        } catch (Exception exc) {
                            throw new RuntimeException(exc);
                        }
                    }
                });
        AnchorPane root = fxmlLoader.load();
        //game.setController(fxmlLoader.getController());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(gameTitle);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}
