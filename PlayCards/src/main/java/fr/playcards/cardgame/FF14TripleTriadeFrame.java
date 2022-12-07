package fr.playcards.cardgame;

//Import part
import fr.playcards.client.IClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

//TODO : Complete this java file

public class FF14TripleTriadeFrame {
    private String gameTitle;

    public FF14TripleTriadeFrame(String gameTitle, CardGame game, IClient client) {
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
