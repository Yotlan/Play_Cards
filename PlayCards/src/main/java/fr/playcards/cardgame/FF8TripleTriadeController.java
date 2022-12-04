package fr.playcards.cardgame;

import fr.playcards.cardgame.card.Card;
import fr.playcards.cardgame.card.FF8Card;
import fr.playcards.client.Client;
import fr.playcards.client.IClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;
import java.util.*;

public class FF8TripleTriadeController implements CardGameController{
    public String UUID;
    public CardGame game;
    @FXML
    public ImageView Player1_Card1 = new ImageView();
    @FXML
    public ImageView Player1_Card2 = new ImageView();
    @FXML
    public ImageView Player1_Card3 = new ImageView();
    @FXML
    public ImageView Player1_Card4 = new ImageView();
    @FXML
    public ImageView Player1_Card5 = new ImageView();
    @FXML
    public ImageView Player2_Card1 = new ImageView();
    @FXML
    public ImageView Player2_Card2 = new ImageView();
    @FXML
    public ImageView Player2_Card3 = new ImageView();
    @FXML
    public ImageView Player2_Card4 = new ImageView();
    @FXML
    public ImageView Player2_Card5 = new ImageView();

    @FXML
    public ImageView Empty_Card11 = new ImageView();
    @FXML
    public ImageView Empty_Card12 = new ImageView();
    @FXML
    public ImageView Empty_Card13 = new ImageView();
    @FXML
    public ImageView Empty_Card21 = new ImageView();
    @FXML
    public ImageView Empty_Card22 = new ImageView();
    @FXML
    public ImageView Empty_Card23 = new ImageView();
    @FXML
    public ImageView Empty_Card31 = new ImageView();
    @FXML
    public ImageView Empty_Card32 = new ImageView();
    @FXML
    public ImageView Empty_Card33 = new ImageView();
    @FXML
    public ImageView SelectedCard = new ImageView();
    @FXML
    public Card SelectedCardEntity = new FF8Card("N/A",0,0,0,0,"N/A");
    public int SelectedCardIndex=-1;

    public int index_player=0; // give index of player that can put the card
    @FXML
    public Label Player1_Pseudo = new Label("N/A");
    @FXML
    public Label Player2_Pseudo = new Label("N/A");
    public IClient client;

    public FF8TripleTriadeController(String UUID) {
        this.UUID = UUID;
    }
    @FXML
    public void initialize() {
        try{
            List<ImageView> EmptyCard = new ArrayList<>(Arrays.asList(Empty_Card11,Empty_Card12,Empty_Card13,Empty_Card21,Empty_Card22,Empty_Card23,Empty_Card31,Empty_Card32,Empty_Card33));
            for(int i=0; i<EmptyCard.size();i++){
                EmptyCard.get(i).setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
            }
            List<Card> P1Card = this.game.getPlayer1Card();
            List<ImageView> P1CardImage = new ArrayList<>(Arrays.asList(Player1_Card1,Player1_Card2,Player1_Card3,Player1_Card4,Player1_Card5));
            for(int i=0; i<P1Card.size();i++){
                Card card = P1Card.get(i);
                Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+card.getName()+".jpg").toFile().toURI().toString());
                P1CardImage.get(i).setImage(image);
            }
            Player1_Pseudo.setText(this.game.getPlayer1Pseudo());
            List<Card> P2Card = this.game.getPlayer2Card();
            List<ImageView> P2CardImage = new ArrayList<>(Arrays.asList(Player2_Card1,Player2_Card2,Player2_Card3,Player2_Card4,Player2_Card5));
            for(int i=0; i<P2Card.size();i++){
                Card card = P2Card.get(i);
                Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+card.getName()+".jpg").toFile().toURI().toString());
                P2CardImage.get(i).setImage(image);
            }
            Player2_Pseudo.setText(this.game.getPlayer2Pseudo());

            this.client.getMainServer().initTurn(this.game.getUUID());
        }catch(Exception e){
            System.out.println("FF8TripleTriadeController initialize method Error : "+e);
        }
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                refresh();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void setGame(CardGame game, IClient client) {
        this.game = game;
        this.client = client;
    }

    public void refresh(){
        try {
            Player1_Pseudo.setText(this.game.getPlayer1Pseudo());
            Player2_Pseudo.setText(this.game.getPlayer2Pseudo());
            Map<String, Card> imageMap = this.client.getFF8Card(this.UUID);

            try {
                Empty_Card11.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("11").getName()+".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                //System.out.println("FF8TripleTriadeController refresh method 11 Error : "+e);
            }
            try {
                Empty_Card21.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("21").getName()+".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                //System.out.println("FF8TripleTriadeController refresh method 21 Error : "+e);
            }
            try {
                Empty_Card31.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("31").getName()+".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                //System.out.println("FF8TripleTriadeController refresh method 31 Error : "+e);
            }
            try {
                Empty_Card12.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("12").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                //System.out.println("FF8TripleTriadeController refresh method 12 Error : "+e);
            }
            try {
                Empty_Card22.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("22").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                //System.out.println("FF8TripleTriadeController refresh method 22 Error : "+e);
            }
            try {
                Empty_Card32.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("32").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                //System.out.println("FF8TripleTriadeController refresh method 32 Error : "+e);
            }
            try {
                Empty_Card13.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("13").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                //System.out.println("FF8TripleTriadeController refresh method 13 Error : "+e);
            }
            try {
                Empty_Card23.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("23").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                //System.out.println("FF8TripleTriadeController refresh method 23 Error : "+e);
            }
            try {
                Empty_Card33.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("33").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                //System.out.println("FF8TripleTriadeController refresh method 33 Error : "+e);
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController refresh method Error : "+e);
        }
    }

    public void P1SelectC1(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                this.SelectedCard = Player1_Card1;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(0);
                this.SelectedCardIndex = 0;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC1 method Error : "+e);
        }
    }

    public void P1SelectC2(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                this.SelectedCard = Player1_Card2;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(1);
                this.SelectedCardIndex = 1;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC2 method Error : "+e);
        }
    }

    public void P1SelectC3(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                this.SelectedCard = Player1_Card3;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(2);
                this.SelectedCardIndex = 2;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC3 method Error : "+e);
        }
    }

    public void P1SelectC4(){

        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                this.SelectedCard = Player1_Card4;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(3);
                this.SelectedCardIndex = 3;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC4 method Error : "+e);
        }
    }

    public void P1SelectC5(){
        try {
            if(this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                this.SelectedCard = Player1_Card5;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(4);
                this.SelectedCardIndex = 4;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC5 method Error : "+e);
        }
    }

    public void P2SelectC1(){
        try {
            if(this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())){
                this.SelectedCard = Player2_Card1;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(0);
                this.SelectedCardIndex = 0;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC1 method Error : "+e);
        }
    }

    public void P2SelectC2(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())){
                this.SelectedCard = Player2_Card2;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(1);
                this.SelectedCardIndex = 1;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC2 method Error : "+e);
        }
    }

    public void P2SelectC3(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())){
                this.SelectedCard = Player2_Card3;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(2);
                this.SelectedCardIndex = 2;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC3 method Error : "+e);
        }
    }

    public void P2SelectC4(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())){
                this.SelectedCard = Player2_Card4;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(3);
                this.SelectedCardIndex = 3;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC4 method Error : "+e);
        }
    }

    public void P2SelectC5(){
        try {
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())){
                this.SelectedCard = Player2_Card5;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(4);
                this.SelectedCardIndex = 4;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC5 method Error : "+e);
        }
    }

    public void displayC11(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            //condition : a space is not occuped by a card AND player played is round of this player
            if ((this.client.getFF8Card(this.UUID).get("11") == null) && (playerID==this.client.getMainServer().getTurn(this.game.getUUID()))){
                this.Empty_Card11.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard11(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1 ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }

                this.client.getMainServer().switchTurn(this.game.getUUID());

                //seletecd cart will null
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC11 method Error : "+e);
        }
    }

    public void displayC21(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID = 2;
            }
            if ((this.client.getFF8Card(this.UUID).get("21") == null) && playerID==this.client.getMainServer().getTurn(this.game.getUUID())){
                this.Empty_Card21.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard21(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1 ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }
                this.client.getMainServer().switchTurn(this.game.getUUID());
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC21 method Error : "+e);
        }
    }

    public void displayC31(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8Card(this.UUID).get("31") == null) && playerID==this.client.getMainServer().getTurn(this.game.getUUID())){
                this.Empty_Card31.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard31(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1 ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }
                this.client.getMainServer().switchTurn(this.game.getUUID());
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC31 method Error : "+e);
        }
    }

    public void displayC12(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8Card(this.UUID).get("12") == null) && playerID==this.client.getMainServer().getTurn(this.game.getUUID())){
                this.Empty_Card12.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard12(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1 ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }
                this.SelectedCard = null;
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC12 method Error : "+e);
        }
    }

    public void displayC22(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8Card(this.UUID).get("22") == null) && playerID==this.client.getMainServer().getTurn(this.game.getUUID())){
                this.Empty_Card22.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard22(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1 ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }
                this.client.getMainServer().switchTurn(this.game.getUUID());
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC22 method Error : "+e);
        }
    }

    public void displayC32(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if((this.client.getFF8Card(this.UUID).get("32") == null) && playerID==this.client.getMainServer().getTurn(this.game.getUUID())){
                this.Empty_Card32.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard32(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1 ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }
                this.client.getMainServer().switchTurn(this.game.getUUID());
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC32 method Error : "+e);
        }
    }

    public void displayC13(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8Card(this.UUID).get("13") == null) && playerID==this.client.getMainServer().getTurn(this.game.getUUID())){
                this.Empty_Card13.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard13(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1 ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }
                this.client.getMainServer().switchTurn(this.game.getUUID());
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC13 method Error : "+e);
        }
    }

    public void displayC23(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8Card(this.UUID).get("23") == null) && playerID==this.client.getMainServer().getTurn(this.game.getUUID())){
                this.Empty_Card23.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard23(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1 ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }
                this.client.getMainServer().switchTurn(this.game.getUUID());
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC23 method Error : "+e);
        }
    }

    public void displayC33(){
        try {
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }
            if ((this.client.getFF8Card(this.UUID).get("33") == null) && playerID==this.client.getMainServer().getTurn(this.game.getUUID())){
                this.Empty_Card33.setImage(this.SelectedCard.getImage());
                this.SelectedCardEntity.setOwner((Client) this.client);
                this.client.getMainServer().displayCard33(SelectedCardEntity,this.game.getUUID(),this.client);
                if (playerID==1 ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 1 :
                            this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 2 :
                            this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 3 :
                            this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 4 :
                            this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                        case 5 :
                            this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                            break;
                    }
                }
                this.client.getMainServer().switchTurn(this.game.getUUID());
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC33 method Error : "+e);
        }
    }

    //TODO : règles de jeu : applicable? same , + ,

}
