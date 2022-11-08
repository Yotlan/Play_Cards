package fr.playcards.cardgame;

import fr.playcards.cardgame.card.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FF8TripleTriadeController implements CardGameController{
    public String UUID;
    public CardGame game;
    @FXML
    public transient ImageView Player1_Card1 = new ImageView();
    @FXML
    public transient ImageView Player1_Card2 = new ImageView();
    @FXML
    public transient ImageView Player1_Card3 = new ImageView();
    @FXML
    public transient ImageView Player1_Card4 = new ImageView();
    @FXML
    public transient ImageView Player1_Card5 = new ImageView();
    @FXML
    public transient ImageView Player2_Card1 = new ImageView();
    @FXML
    public transient ImageView Player2_Card2 = new ImageView();
    @FXML
    public transient ImageView Player2_Card3 = new ImageView();
    @FXML
    public transient ImageView Player2_Card4 = new ImageView();
    @FXML
    public transient ImageView Player2_Card5 = new ImageView();

    public FF8TripleTriadeController(String UUID) {
        this.UUID = UUID;
    }
    @FXML
    public void initialize() {
        try{
            List<Card> P1Card = this.game.getPlayer1Card();
            List<ImageView> P1CardImage = new ArrayList<>(Arrays.asList(Player1_Card1,Player1_Card2,Player1_Card3,Player1_Card4,Player1_Card5));
            for(int i=0; i<P1Card.size();i++){
                Card card = P1Card.get(i);
                Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+card.getName()+".jpg").toFile().toURI().toString());
                P1CardImage.get(i).setImage(image);
            }
            List<Card> P2Card = this.game.getPlayer2Card();
            List<ImageView> P2CardImage = new ArrayList<>(Arrays.asList(Player2_Card1,Player2_Card2,Player2_Card3,Player2_Card4,Player2_Card5));
            for(int i=0; i<P2Card.size();i++){
                Card card = P2Card.get(i);
                Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+card.getName()+".jpg").toFile().toURI().toString());
                P2CardImage.get(i).setImage(image);
            }
        }catch(Exception e){
            System.out.println("FF8TripleTriadeController initialize method Error : "+e);
        }
    }

    public void setGame(CardGame game) {
        this.game = game;
    }
}
