package fr.playcards.cardgame;

//Import part
import fr.playcards.client.IClient;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.RemoteException;

/*
@author Yotlan LE CROM

This class have for goal to initialize and launch a FF8 Triple Triade game frame.
 */

public class FF8TripleTriadeFrame {
    private String gameTitle;
    public CardGame game;
    public IClient client;

    public FF8TripleTriadeFrame(String gameTitle, CardGame game, IClient client) {
        this.gameTitle = gameTitle;
        this.game = game;
        this.client = client;
    }

    /*
    @throws IOException

    This method initialize and launch the FF8 Triple Triade game frame. Especially, if a player join the FF8 Triple
    Triade game frame and if this player is the first player, we'll create a new FF8TripleTriadeController by passing
    FF8 Triple Triade UUID game in his constructor. Moreover, we link this frame to a fxml file (with no linked
    controller because we want for 2 clients how join the game to have the same controller ! If a second player join the
    game, we link this client with an already created controller who's created for the FF8 Triple Triade UUID Game.
     */

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
                try {
                    return clazz.newInstance();
                } catch (Exception exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
        BorderPane root = fxmlLoader.load();
        Platform.setImplicitExit(false);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(gameTitle);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}
