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

    //TODO : gameBoard : 0 for no card, and 1 for player 1 , 2 for player 2;
    // if a case have another value than 0, player can not put their card in this case
    // indice respectly 1 to 9 , ligne per ligne
    // 0  0  0
    // 0  1  2
    // 0  1  0
    public int [] gameBoard=new int[9];
    // TODO : gameBoardCard to save Card on the board
    public Card [] gameBoardCard=new Card[9];

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

            Arrays.fill(gameBoard,0);
            Arrays.fill(gameBoardCard,null);
            index_player=1;
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
                System.out.println("FF8TripleTriadeController refresh method 11 Error : "+e);
            }
            try {
                Empty_Card21.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("21").getName()+".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                System.out.println("FF8TripleTriadeController refresh method 21 Error : "+e);
            }
            try {
                Empty_Card31.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/"+imageMap.get("31").getName()+".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                System.out.println("FF8TripleTriadeController refresh method 31 Error : "+e);
            }
            try {
                Empty_Card12.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("12").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                System.out.println("FF8TripleTriadeController refresh method 12 Error : "+e);
            }
            try {
                Empty_Card22.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("22").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                System.out.println("FF8TripleTriadeController refresh method 22 Error : "+e);
            }
            try {
                Empty_Card32.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("32").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                System.out.println("FF8TripleTriadeController refresh method 32 Error : "+e);
            }
            try {
                Empty_Card13.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("13").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                System.out.println("FF8TripleTriadeController refresh method 13 Error : "+e);
            }
            try {
                Empty_Card23.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("23").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                System.out.println("FF8TripleTriadeController refresh method 23 Error : "+e);
            }
            try {
                Empty_Card33.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl1/" + imageMap.get("33").getName() + ".jpg").toFile().toURI().toString()));
            } catch (Exception e) {
                System.out.println("FF8TripleTriadeController refresh method 33 Error : "+e);
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
            if (this.gameBoard[0]==0){
                this.Empty_Card11.setImage(this.SelectedCard.getImage());
                this.client.getMainServer().displayCard11(SelectedCardEntity,this.game.getUUID(),this.client);
                //TODO: display of Player card must be empty
                //remove card from player card
                if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo()) ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    this.gameBoard[0]=1;

                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    this.gameBoard[0]=2;
                }
                this.gameBoardCard[0]=this.SelectedCardEntity;

                //seletecd cart will null
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC11 method Error : "+e);
        }
    }

    public void displayC21(){

        try {
            if (this.gameBoard[3]==0){
                this.Empty_Card21.setImage(this.SelectedCard.getImage());
                this.client.getMainServer().displayCard21(SelectedCardEntity,this.game.getUUID(),this.client);
                if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo()) ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    this.gameBoard[3]=1;
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    this.gameBoard[3]=2;
                }
                this.gameBoardCard[3]=this.SelectedCardEntity;
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC21 method Error : "+e);
        }
    }

    public void displayC31(){

        try {
            if (this.gameBoard[6]==0){
                this.Empty_Card31.setImage(this.SelectedCard.getImage());
                this.client.getMainServer().displayCard31(SelectedCardEntity,this.game.getUUID(),this.client);
                if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo()) ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    this.gameBoard[6]=1;
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    this.gameBoard[6]=2;
                }
                this.gameBoardCard[6]=this.SelectedCardEntity;
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC31 method Error : "+e);
        }
    }

    public void displayC12(){

        try {
            if (this.gameBoard[1]==0){
                this.Empty_Card12.setImage(this.SelectedCard.getImage());
                this.client.getMainServer().displayCard12(SelectedCardEntity,this.game.getUUID(),this.client);
                if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo()) ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    this.gameBoard[1]=1;
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    this.gameBoard[1]=2;
                }
                this.gameBoardCard[1]=this.SelectedCardEntity;
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC12 method Error : "+e);
        }
    }

    public void displayC22(){

        try {
            if (this.gameBoard[4]==0){
                this.Empty_Card22.setImage(this.SelectedCard.getImage());
                this.client.getMainServer().displayCard22(SelectedCardEntity,this.game.getUUID(),this.client);
                if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo()) ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    this.gameBoard[4]=1;
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    this.gameBoard[4]=2;
                }
                this.gameBoardCard[4]=this.SelectedCardEntity;
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC22 method Error : "+e);
        }
    }

    public void displayC32(){

        try {
            if(gameBoard[7]==0){
                this.Empty_Card32.setImage(this.SelectedCard.getImage());
                this.client.getMainServer().displayCard32(SelectedCardEntity,this.game.getUUID(),this.client);
                if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo()) ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    this.gameBoard[7]=1;
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    this.gameBoard[7]=2;
                }
                this.gameBoardCard[7]=this.SelectedCardEntity;
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC32 method Error : "+e);
        }
    }

    public void displayC13(){
        try {
            if (this.gameBoard[2]==0){
                this.Empty_Card13.setImage(this.SelectedCard.getImage());
                this.client.getMainServer().displayCard13(SelectedCardEntity,this.game.getUUID(),this.client);
                if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo()) ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    this.gameBoard[2]=1;
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    this.gameBoard[2]=2;
                }
                this.gameBoardCard[2]=this.SelectedCardEntity;
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC13 method Error : "+e);
        }
    }

    public void displayC23(){
        try {
            if (this.gameBoard[5]==0){
                this.Empty_Card23.setImage(this.SelectedCard.getImage());
                this.client.getMainServer().displayCard23(SelectedCardEntity,this.game.getUUID(),this.client);
                if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo()) ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    this.gameBoard[5]=1;
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    this.gameBoard[5]=2;
                }
                this.gameBoardCard[5]=this.SelectedCardEntity;
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC23 method Error : "+e);
        }
    }

    public void displayC33(){
        try {
            if (this.gameBoard[8]==0){
                this.Empty_Card33.setImage(this.SelectedCard.getImage());
                this.client.getMainServer().displayCard33(SelectedCardEntity,this.game.getUUID(),this.client);
                if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo()) ){
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    this.gameBoard[8]=1;
                }else{
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    this.gameBoard[8]=2;
                }
                this.gameBoardCard[8]=this.SelectedCardEntity;
                this.SelectedCard = null;
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC33 method Error : "+e);
        }
    }

}
