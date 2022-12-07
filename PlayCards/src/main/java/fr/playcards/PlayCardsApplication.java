package fr.playcards;

//Import part
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
@author Yotlan LE CROM

This class have for goal to initialize and launch the main frame of the PlayCards application.
 */

public class PlayCardsApplication extends Application {

    /*
    @param Stage stage

    This method take a stage who's a launched frame and link with the main controller. Then we adjust the main frame
    for the screen and set his title. To conclude, we set the close instruction to close properly the frame.
     */
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

    /*
    This method launch the PlayCards application's frame for one client.
     */
    public static void main(String[] args) {
        launch(args);
    }
}