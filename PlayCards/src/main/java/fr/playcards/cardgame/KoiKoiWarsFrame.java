package fr.playcards.cardgame;

//Import part
import fr.playcards.client.IClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

//TODO : Complete this java file

public class KoiKoiWarsFrame {
    private String gameTitle;

    public KoiKoiWarsFrame(String gameTitle, CardGame game, IClient client) {
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
