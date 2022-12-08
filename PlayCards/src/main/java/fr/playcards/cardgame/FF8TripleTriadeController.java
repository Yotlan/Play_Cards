package fr.playcards.cardgame;

//Import part
import fr.playcards.cardgame.card.Card;
import fr.playcards.cardgame.card.FF8Card;
import fr.playcards.client.Client;
import fr.playcards.client.IClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.nio.file.Paths;
import java.rmi.RemoteException;
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
    public Card SelectedCardEntity = new FF8Card("N/A",0,0,0,0,0,"N/A");
    public int SelectedCardIndex=-1;
    @FXML
    public Label Player1_Pseudo = new Label("N/A");
    @FXML
    public Label Player2_Pseudo = new Label("N/A");
    @FXML
    public Label Message = new Label("");
    public IClient client;
    @FXML
    public Circle c11 = new Circle();
    @FXML
    public Circle c12 = new Circle();
    @FXML
    public Circle c13 = new Circle();
    @FXML
    public Circle c21 = new Circle();
    @FXML
    public Circle c22 = new Circle();
    @FXML
    public Circle c23 = new Circle();
    @FXML
    public Circle c31 = new Circle();
    @FXML
    public Circle c32 = new Circle();
    @FXML
    public Circle c33 = new Circle();

    public FF8TripleTriadeController(String UUID) {
        this.UUID = UUID;
    }

    /*
    This method initialize all the information we can have to the controller of FF8 Triple Triade game.
     */

    @FXML
    public synchronized void initialize() {
        try{

            //Display all the 9 middle card like empty card
            List<ImageView> EmptyCard = new ArrayList<>(Arrays.asList(Empty_Card11,Empty_Card12,Empty_Card13,Empty_Card21,Empty_Card22,Empty_Card23,Empty_Card31,Empty_Card32,Empty_Card33));
            for(int i=0; i<EmptyCard.size();i++){
                EmptyCard.get(i).setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
            }

            //Display all the 5 player 1 cards
            List<Card> P1Card = this.game.getPlayer1Card();
            List<ImageView> P1CardImage = new ArrayList<>(Arrays.asList(Player1_Card1,Player1_Card2,Player1_Card3,Player1_Card4,Player1_Card5));
            for(int i=0; i<P1Card.size();i++){
                Card card = P1Card.get(i);
                Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+card.getLevel()+"/"+card.getName()+".jpg").toFile().toURI().toString());
                P1CardImage.get(i).setImage(image);
            }

            //Add the client to the server
            this.client.getMainServer().initFF8GameClientList(this.game.getUUID(),this.client);

            //Display the player 1 pseudo
            Player1_Pseudo.setText(this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).get(0).getClientPseudo());

            //Display all the 5 player 2 cards
            List<Card> P2Card = this.game.getPlayer2Card();
            List<ImageView> P2CardImage = new ArrayList<>(Arrays.asList(Player2_Card1,Player2_Card2,Player2_Card3,Player2_Card4,Player2_Card5));
            for(int i=0; i<P2Card.size();i++){
                Card card = P2Card.get(i);
                Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+card.getLevel()+"/"+card.getName()+".jpg").toFile().toURI().toString());
                P2CardImage.get(i).setImage(image);
            }

            //Run later because we not have already the information and we'll have these information later
            Platform.runLater(() -> {
                try {

                    //If we have already 2 clients in the game, we can display the player 2 pseudo
                    if (this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).size() > 1) {
                        Player2_Pseudo.setText(this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).get(1).getClientPseudo());
                    }
                } catch (Exception e) {
                    System.out.println("FF8TripleTriadeController initialize method Error : "+e);
                }
            });

            //Initialize the turn of the card game by beginning with the player 1
            this.client.getMainServer().initTurn(this.game.getUUID());
        }catch(Exception e){
            System.out.println("FF8TripleTriadeController initialize method Error : "+e);
        }

        //Create a thread who called refresh method each second
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

    public synchronized void refresh(){
        try {

            //Display player 1 pseudo
            Player1_Pseudo.setText(this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).get(0).getClientPseudo());

            //Run later because maybe we not have already the information we need
            Platform.runLater(() -> {
                try {

                    //If we have 2 client in the card game, display player 2 pseudo
                    if (this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).size() > 1) {
                        Player2_Pseudo.setText(this.client.getMainServer().getFF8GameClientList(this.game.getUUID()).get(1).getClientPseudo());
                    }
                } catch (Exception e) {
                    System.out.println("FF8TripleTriadeController refresh method Error : "+e);
                }
            });

            //Display all 5 player 1 cards
            List<Card> P1Card = this.game.getPlayer1Card();
            List<ImageView> P1CardImage = new ArrayList<>(Arrays.asList(Player1_Card1,Player1_Card2,Player1_Card3,Player1_Card4,Player1_Card5));
            for(int i=0; i<P1Card.size();i++){
                Card card = P1Card.get(i);
                if(card.getName().equals("Empty")){
                    Image image = new Image(Paths.get("../Triple_Triade/FF8/img/" + card.getName() + ".jpg").toFile().toURI().toString());
                    P1CardImage.get(i).setImage(image);
                }else {
                    Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+card.getLevel()+"/" + card.getName() + ".jpg").toFile().toURI().toString());
                    P1CardImage.get(i).setImage(image);
                }
            }

            //Display all 5 player 2 cards
            List<Card> P2Card = this.game.getPlayer2Card();
            List<ImageView> P2CardImage = new ArrayList<>(Arrays.asList(Player2_Card1,Player2_Card2,Player2_Card3,Player2_Card4,Player2_Card5));
            for(int i=0; i<P2Card.size();i++){
                Card card = P2Card.get(i);
                if(card.getName().equals("Empty")){
                    Image image = new Image(Paths.get("../Triple_Triade/FF8/img/" + card.getName() + ".jpg").toFile().toURI().toString());
                    P2CardImage.get(i).setImage(image);
                }else {
                    Image image = new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+card.getLevel()+"/" + card.getName() + ".jpg").toFile().toURI().toString());
                    P2CardImage.get(i).setImage(image);
                }
            }

            //Getting all the name of the 9 middle cards to display it in the middle
            Map<String, String> imageMap = this.client.getFF8CardName(this.UUID);

            //Getting all the level of the 9 middle cards to display it in the middle
            Map<String, Integer> cardLevel = this.client.getFF8CardLevel(this.UUID);

            //Getting all the owner name of the 9 middle cards to change pawn's color
            Map<String, String> cardOwner = this.client.getFF8CardOwner(this.UUID);

            //Run later because maybe we not have already the necessary information
            Platform.runLater(() -> {
                try {

                    //Initialize value before checking if the game is end
                    boolean isEnd = true;
                    int P1Pawn = 0;
                    int P2Pawn = 0;

                    //Check if all the 9 cards is displayed
                    for (String owner : cardOwner.values()) {
                        if (owner == null) {
                            isEnd = false;
                        } else {

                            //Update the score for cards owner's
                            if (owner.equals(Player1_Pseudo.getText())) {
                                P1Pawn++;
                            } else {
                                P2Pawn++;
                            }
                        }
                    }

                    //If the game is end
                    if (isEnd) {

                        //Getting player 1 and player 2 cards and check which one have 1 cards left, to add him +1
                        List<Card> P1EndCard = this.game.getPlayer1Card();
                        List<Card> P2EndCard = this.game.getPlayer2Card();
                        for (Card card : P1EndCard) {
                            if (!(card.getName().equals("Empty"))) {
                                P1Pawn++;
                            }
                        }
                        for (Card card : P2EndCard) {
                            if (!(card.getName().equals("Empty"))) {
                                P2Pawn++;
                            }
                        }

                        //Check who win and change the turn's message by the score and the name of the winner
                        if (P1Pawn > P2Pawn) {
                            showAlertWinning(Player1_Pseudo.getText(), Player2_Pseudo.getText(), P1Pawn, P2Pawn);
                        } else if (P2Pawn > P1Pawn) {
                            showAlertWinning(Player2_Pseudo.getText(), Player1_Pseudo.getText(), P2Pawn, P1Pawn);
                        } else {
                            showAlertDraw(Player1_Pseudo.getText(), Player2_Pseudo.getText(), P1Pawn, P2Pawn);
                        }
                    } else {

                        //Getting the player's turn and change the turn's message by displaying his name
                        int turn = this.client.getMainServer().getTurn(this.game.getUUID());
                        if (turn == 1) {
                            Message.setText("It's " + Player1_Pseudo.getText() + "'s turn !");
                        } else {
                            Message.setText("It's " + Player2_Pseudo.getText() + "'s turn !");
                        }
                    }
                } catch(Exception e) {
                    System.out.println("FF8TripleTriadeController refresh method Error : " + e);
                }
            });

            //Check if a card is displayed in corresponding position, then display it in the screen of clients
            //Moreover, link the pawn's color with the card's owner color
            if(imageMap.get("11")!=null){
                Empty_Card11.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+cardLevel.get("11")+"/"+imageMap.get("11")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("11").equals(Player1_Pseudo.getText())){
                    c11.setFill(Color.BLUE);
                }else{
                    c11.setFill(Color.RED);
                }
            }
            if(imageMap.get("21")!=null){
                Empty_Card21.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+cardLevel.get("21")+"/"+imageMap.get("21")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("21").equals(Player1_Pseudo.getText())){
                    c21.setFill(Color.BLUE);
                }else{
                    c21.setFill(Color.RED);
                }
            }
            if(imageMap.get("31")!=null){
                Empty_Card31.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+cardLevel.get("31")+"/"+imageMap.get("31")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("31").equals(Player1_Pseudo.getText())){
                    c31.setFill(Color.BLUE);
                }else{
                    c31.setFill(Color.RED);
                }
            }
            if(imageMap.get("12")!=null){
                Empty_Card12.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+cardLevel.get("12")+"/"+imageMap.get("12")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("12").equals(Player1_Pseudo.getText())){
                    c12.setFill(Color.BLUE);
                }else{
                    c12.setFill(Color.RED);
                }
            }
            if(imageMap.get("22")!=null){
                Empty_Card22.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+cardLevel.get("22")+"/"+imageMap.get("22")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("22").equals(Player1_Pseudo.getText())){
                    c22.setFill(Color.BLUE);
                }else{
                    c22.setFill(Color.RED);
                }
            }
            if(imageMap.get("32")!=null){
                Empty_Card32.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+cardLevel.get("32")+"/"+imageMap.get("32")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("32").equals(Player1_Pseudo.getText())){
                    c32.setFill(Color.BLUE);
                }else{
                    c32.setFill(Color.RED);
                }
            }
            if(imageMap.get("13")!=null){
                Empty_Card13.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+cardLevel.get("13")+"/"+imageMap.get("13")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("13").equals(Player1_Pseudo.getText())){
                    c13.setFill(Color.BLUE);
                }else{
                    c13.setFill(Color.RED);
                }
            }
            if(imageMap.get("23")!=null){
                Empty_Card23.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+cardLevel.get("23")+"/"+imageMap.get("23")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("23").equals(Player1_Pseudo.getText())){
                    c23.setFill(Color.BLUE);
                }else{
                    c23.setFill(Color.RED);
                }
            }
            if(imageMap.get("33")!=null){
                Empty_Card33.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/lvl"+cardLevel.get("33")+"/"+imageMap.get("33")+".jpg").toFile().toURI().toString()));
                if(cardOwner.get("33").equals(Player1_Pseudo.getText())){
                    c33.setFill(Color.BLUE);
                }else{
                    c33.setFill(Color.RED);
                }
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController refresh method Error : "+e);
        }
    }

    /*
    This method is called when a player select a card he already played or when he try to select the opponent cards
     */

    private void showAlertSelect() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText("You can't select this card because you already play it or it's not your card !");
        alert.showAndWait();
    }

    /*
    This method is called when a player display a card where a card is already display or when it's not his turn
     */

    private void showAlertDisplay() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText("You can't display this card here because someone else display a card here or it's not your turn !");
        alert.showAndWait();
    }

    /*
    @param String winner
    @param String looser
    @param nbWinPawn
    @param nbLoosePawn

    These methods are called when the game end and change the turn's message by the corresponding score message
     */

    private void showAlertWinning(String winner, String looser, int nbWinPawn, int nbLoosePawn) {
        Message.setText("Congratulation to "+winner+" ! He's the winner of this game ! He win against "+looser+" with a score of "+Integer.toString(nbWinPawn)+"-"+Integer.toString(nbLoosePawn)+" !");
    }
    private void showAlertDraw(String winner, String looser, int nbWinPawn, int nbLoosePawn) {
        Message.setText("Congratulation to both "+winner+" and "+looser+" ! It's a draw ! The score is "+Integer.toString(nbWinPawn)+"-"+Integer.toString(nbLoosePawn)+" !");
    }

    /*
    These methods are called when a player choose a card by clicking on it
     */

    public void P1SelectC1(){
        try {

            //Check if the player can choose this card
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(0).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card1;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(0);
                this.SelectedCardIndex = 0;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC1 method Error : "+e);
        }
    }
    public void P1SelectC2(){
        try {

            //Check if the player can choose this card
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(1).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card2;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(1);
                this.SelectedCardIndex = 1;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC2 method Error : "+e);
        }
    }
    public void P1SelectC3(){
        try {

            //Check if the player can choose this card
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(2).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card3;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(2);
                this.SelectedCardIndex = 2;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC3 method Error : "+e);
        }
    }
    public void P1SelectC4(){
        try {

            //Check if the player can choose this card
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(3).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card4;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(3);
                this.SelectedCardIndex = 3;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC4 method Error : "+e);
        }
    }
    public void P1SelectC5(){
        try {

            //Check if the player can choose this card
            if(this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())
                    && !(this.game.getPlayer1Card().get(4).getName().equals("Empty"))
            ){
                this.SelectedCard = Player1_Card5;
                this.SelectedCardEntity = this.game.getPlayer1Card().get(4);
                this.SelectedCardIndex = 4;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P1SelectC5 method Error : "+e);
        }
    }
    public void P2SelectC1(){
        try {

            //Check if the player can choose this card
            if(this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(0).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card1;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(0);
                this.SelectedCardIndex = 0;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC1 method Error : "+e);
        }
    }
    public void P2SelectC2(){
        try {

            //Check if the player can choose this card
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(1).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card2;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(1);
                this.SelectedCardIndex = 1;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC2 method Error : "+e);
        }
    }
    public void P2SelectC3(){
        try {

            //Check if the player can choose this card
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(2).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card3;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(2);
                this.SelectedCardIndex = 2;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC3 method Error : "+e);
        }
    }
    public void P2SelectC4(){
        try {

            //Check if the player can choose this card
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(3).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card4;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(3);
                this.SelectedCardIndex = 3;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC4 method Error : "+e);
        }
    }
    public void P2SelectC5(){
        try {

            //Check if the player can choose this card
            if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())
                    && !(this.game.getPlayer2Card().get(4).getName().equals("Empty"))
            ){
                this.SelectedCard = Player2_Card5;
                this.SelectedCardEntity = this.game.getPlayer2Card().get(4);
                this.SelectedCardIndex = 4;
            }else{
                showAlertSelect();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController P2SelectC5 method Error : "+e);
        }
    }

    /*
    These methods are called when a player display a card at the corresponding position
     */

    public void displayC11(){
        try {

            //Determine the playerID before checking it's his turn or not
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }

            //Check if we can display the card at this position, and if the current player have the right to display it
            if ((this.client.getFF8CardName(this.UUID).get("11") == null)
                    && (playerID==this.client.getMainServer().getTurn(this.game.getUUID()))
            ){

                //Set the empty card image at this position by the selected card image
                this.Empty_Card11.setImage(this.SelectedCard.getImage());

                //Set the owner name of this card by this client
                this.SelectedCardEntity.setOwner((Client) this.client);

                //Send the information to the server to spread it to all clients
                this.client.getMainServer().displayCard11(SelectedCardEntity,this.game.getUUID(),this.client);

                //Check which player's turn
                if (playerID==1){

                    //Call soft remove and display the empty card at the position of player 1 selected card in his hand
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{

                    //Call soft remove and display the empty card at the position of player 2 selected card in his hand
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }

                //Reinitialize selected card, and called flip rule at this position before changing the turn to the next player
                this.SelectedCard = null;
                this.flipRule("11", new ArrayList<>(Arrays.asList(this.SelectedCardEntity.getName())));
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC11 method Error : "+e);
        }
    }
    public void displayC21(){
        try {

            //Determine the playerID before checking it's his turn or not
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID = 2;
            }

            //Check if we can display the card at this position, and if the current player have the right to display it
            if ((this.client.getFF8CardName(this.UUID).get("21") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){

                //Set the empty card image at this position by the selected card image
                this.Empty_Card21.setImage(this.SelectedCard.getImage());

                //Set the owner name of this card by this client
                this.SelectedCardEntity.setOwner((Client) this.client);

                //Send the information to the server to spread it to all clients
                this.client.getMainServer().displayCard21(SelectedCardEntity,this.game.getUUID(),this.client);

                //Check player's turn
                if (playerID==1){

                    //Call soft remove and display the empty card at the position of player 1 selected card in his hand
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{

                    //Call soft remove and display the empty card at the position of player 2 selected card in his hand
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }

                //Reinitialize selected card, and called flip rule at this position before changing the turn to the next player
                this.SelectedCard = null;
                this.flipRule("21", new ArrayList<>(Arrays.asList(this.SelectedCardEntity.getName())));
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC21 method Error : "+e);
        }
    }
    public void displayC31(){
        try {

            //Determine the playerID before checking it's his turn or not
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }

            //Check if we can display the card at this position, and if the current player have the right to display it
            if ((this.client.getFF8CardName(this.UUID).get("31") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){

                //Set the empty card image at this position by the selected card image
                this.Empty_Card31.setImage(this.SelectedCard.getImage());

                //Set the owner name of this card by this client
                this.SelectedCardEntity.setOwner((Client) this.client);

                //Send the information to the server to spread it to all clients
                this.client.getMainServer().displayCard31(SelectedCardEntity,this.game.getUUID(),this.client);

                //Check player's turn
                if (playerID==1){

                    //Call soft remove and display the empty card at the position of player 1 selected card in his hand
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{

                    //Call soft remove and display the empty card at the position of player 2 selected card in his hand
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }

                //Reinitialize selected card, and called flip rule at this position before changing the turn to the next player
                this.SelectedCard = null;
                this.flipRule("31", new ArrayList<>(Arrays.asList(this.SelectedCardEntity.getName())));
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC31 method Error : "+e);
        }
    }
    public void displayC12(){
        try {

            //Determine the playerID before checking it's his turn or not
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }

            //Check if we can display the card at this position, and if the current player have the right to display it
            if ((this.client.getFF8CardName(this.UUID).get("12") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){

                //Set the empty card image at this position by the selected card image
                this.Empty_Card12.setImage(this.SelectedCard.getImage());

                //Set the owner name of this card by this client
                this.SelectedCardEntity.setOwner((Client) this.client);

                //Send the information to the server to spread it to all clients
                this.client.getMainServer().displayCard12(SelectedCardEntity,this.game.getUUID(),this.client);

                //Check player's turn
                if (playerID==1){

                    //Call soft remove and display the empty card at the position of player 1 selected card in his hand
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{

                    //Call soft remove and display the empty card at the position of player 2 selected card in his hand
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }

                //Reinitialize selected card, and called flip rule at this position before changing the turn to the next player
                this.SelectedCard = null;
                this.flipRule("12", new ArrayList<>(Arrays.asList(this.SelectedCardEntity.getName())));
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC12 method Error : "+e);
        }
    }
    public void displayC22(){
        try {

            //Determine the playerID before checking it's his turn or not
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }

            //Check if we can display the card at this position, and if the current player have the right to display it
            if ((this.client.getFF8CardName(this.UUID).get("22") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){

                //Set the empty card image at this position by the selected card image
                this.Empty_Card22.setImage(this.SelectedCard.getImage());

                //Set the owner name of this card by this client
                this.SelectedCardEntity.setOwner((Client) this.client);

                //Send the information to the server to spread it to all clients
                this.client.getMainServer().displayCard22(SelectedCardEntity,this.game.getUUID(),this.client);

                //Check player's turn
                if (playerID==1){

                    //Call soft remove and display the empty card at the position of player 1 selected card in his hand
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{

                    //Call soft remove and display the empty card at the position of player 2 selected card in his hand
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }

                //Reinitialize selected card, and called flip rule at this position before changing the turn to the next player
                this.SelectedCard = null;
                this.flipRule("22", new ArrayList<>(Arrays.asList(this.SelectedCardEntity.getName())));
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC22 method Error : "+e);
        }
    }
    public void displayC32(){
        try {

            //Determine the playerID before checking it's his turn or not
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }

            //Check if we can display the card at this position, and if the current player have the right to display it
            if((this.client.getFF8CardName(this.UUID).get("32") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){

                //Set the empty card image at this position by the selected card image
                this.Empty_Card32.setImage(this.SelectedCard.getImage());

                //Set the owner name of this card by this client
                this.SelectedCardEntity.setOwner((Client) this.client);

                //Send the information to the server to spread it to all clients
                this.client.getMainServer().displayCard32(SelectedCardEntity,this.game.getUUID(),this.client);

                //Check player's turn
                if (playerID==1){

                    //Call soft remove and display the empty card at the position of player 1 selected card in his hand
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{

                    //Call soft remove and display the empty card at the position of player 2 selected card in his hand
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }

                //Reinitialize selected card, and called flip rule at this position before changing the turn to the next player
                this.SelectedCard = null;
                this.flipRule("32", new ArrayList<>(Arrays.asList(this.SelectedCardEntity.getName())));
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC32 method Error : "+e);
        }
    }
    public void displayC13(){
        try {

            //Determine the playerID before checking it's his turn or not
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }

            //Check if we can display the card at this position, and if the current player have the right to display it
            if ((this.client.getFF8CardName(this.UUID).get("13") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){

                //Set the empty card image at this position by the selected card image
                this.Empty_Card13.setImage(this.SelectedCard.getImage());

                //Set the owner name of this card by this client
                this.SelectedCardEntity.setOwner((Client) this.client);

                //Send the information to the server to spread it to all clients
                this.client.getMainServer().displayCard13(SelectedCardEntity,this.game.getUUID(),this.client);

                //Check player's turn
                if (playerID==1){

                    //Call soft remove and display the empty card at the position of player 1 selected card in his hand
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{

                    //Call soft remove and display the empty card at the position of player 2 selected card in his hand
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }

                //Reinitialize selected card, and called flip rule at this position before changing the turn to the next player
                this.SelectedCard = null;
                this.flipRule("13", new ArrayList<>(Arrays.asList(this.SelectedCardEntity.getName())));
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC13 method Error : "+e);
        }
    }
    public void displayC23(){
        try {

            //Determine the playerID before checking it's his turn or not
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }

            //Check if we can display the card at this position, and if the current player have the right to display it
            if ((this.client.getFF8CardName(this.UUID).get("23") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){

                //Set the empty card image at this position by the selected card image
                this.Empty_Card23.setImage(this.SelectedCard.getImage());

                //Set the owner name of this card by this client
                this.SelectedCardEntity.setOwner((Client) this.client);

                //Send the information to the server to spread it to all clients
                this.client.getMainServer().displayCard23(SelectedCardEntity,this.game.getUUID(),this.client);

                //Check player's turn
                if (playerID==1){

                    //Call soft remove and display the empty card at the position of player 1 selected card in his hand
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{

                    //Call soft remove and display the empty card at the position of player 2 selected card in his hand
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }

                //Reinitialize selected card, and called flip rule at this position before changing the turn to the next player
                this.SelectedCard = null;
                this.flipRule("23", new ArrayList<>(Arrays.asList(this.SelectedCardEntity.getName())));
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC23 method Error : "+e);
        }
    }
    public void displayC33(){
        try {

            //Determine the playerID before checking it's his turn or not
            int playerID=0;
            if (this.client.getClientPseudo().equals(this.game.getPlayer1Pseudo())){
                playerID=1;
            } else if (this.client.getClientPseudo().equals(this.game.getPlayer2Pseudo())) {
                playerID=2;
            }

            //Check if we can display the card at this position, and if the current player have the right to display it
            if ((this.client.getFF8CardName(this.UUID).get("33") == null)
                    && playerID==this.client.getMainServer().getTurn(this.game.getUUID())
            ){

                //Set the empty card image at this position by the selected card image
                this.Empty_Card33.setImage(this.SelectedCard.getImage());

                //Set the owner name of this card by this client
                this.SelectedCardEntity.setOwner((Client) this.client);

                //Send the information to the server to spread it to all clients
                this.client.getMainServer().displayCard33(SelectedCardEntity,this.game.getUUID(),this.client);

                //Check player's turn
                if (playerID==1){

                    //Call soft remove and display the empty card at the position of player 1 selected card in his hand
                    this.game.removePlayer1Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player1_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player1_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player1_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player1_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player1_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }else{

                    //Call soft remove and display the empty card at the position of player 2 selected card in his hand
                    this.game.removePlayer2Card(this.SelectedCardIndex);
                    switch (this.SelectedCardIndex) {
                        case 0 ->
                                this.Player2_Card1.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 1 ->
                                this.Player2_Card2.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 2 ->
                                this.Player2_Card3.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 3 ->
                                this.Player2_Card4.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                        case 4 ->
                                this.Player2_Card5.setImage(new Image(Paths.get("../Triple_Triade/FF8/img/Empty.jpg").toFile().toURI().toString()));
                    }
                }

                //Reinitialize selected card, and called flip rule at this position before changing the turn to the next player
                this.SelectedCard = null;
                this.flipRule("33", new ArrayList<>(Arrays.asList(this.SelectedCardEntity.getName())));
                this.client.getMainServer().switchTurn(this.game.getUUID());
            }else{
                showAlertDisplay();
            }
        } catch (Exception e) {
            System.out.println("FF8TripleTriadeController displayC33 method Error : "+e);
        }
    }

    /*
    @param String position
    @param List<String> visitedCard

    @throws RemoteException

    This method is called when a card is display somewhere on the board, so when we need to check if the card around can
    be flip or not
     */

    public synchronized void flipRule(String position, List<String> visitedCard) throws RemoteException {

        //Initialize turn and position
        boolean turnRight=false;
        boolean turnDown=false;
        boolean turnLeft=false;
        boolean turnTop=false;
        int x;
        int y;
        int positionNumeric_x = Integer.parseInt(String.valueOf(position.charAt(0)));
        int positionNumeric_y = Integer.parseInt(String.valueOf(position.charAt(1)));

        if(this.checkSame(positionNumeric_x,positionNumeric_y)){
            //Same rule : called again Flip rule
            if (positionNumeric_y<3){
                turnRight=true;
            }
            if (positionNumeric_y>1){
                turnLeft=true;
            }
            if (positionNumeric_x>1){
                turnTop=true;
            }
            if (positionNumeric_x<3){
                turnDown=true;
            }

            if (turnRight){
                x = Integer.parseInt(String.valueOf(position.charAt(0)));
                y = Integer.parseInt(String.valueOf(position.charAt(1)))+1;

                if(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))!=null) {
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
            }
            if (turnLeft){
                x = Integer.parseInt(String.valueOf(position.charAt(0)) );
                y = Integer.parseInt(String.valueOf(position.charAt(1)))-1;

                if(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))!=null) {
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
            }
            if (turnTop){
                x = Integer.parseInt(String.valueOf(position.charAt(0)))-1;
                y = Integer.parseInt(String.valueOf(position.charAt(1)));

                if(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))!=null) {
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
            }
            if (turnDown){
                x = Integer.parseInt(String.valueOf(position.charAt(0)))+1;
                y = Integer.parseInt(String.valueOf(position.charAt(1)));

                if(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))!=null) {
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
            }
        }else if(this.checkPlus(positionNumeric_x,positionNumeric_y)){
            //Plus rule : called again Flip rule
            if (positionNumeric_y<3){
                turnRight=true;
            }
            if (positionNumeric_y>1){
                turnLeft=true;
            }
            if (positionNumeric_x>1){
                turnTop=true;
            }
            if (positionNumeric_x<3){
                turnDown=true;
            }

            if (turnRight){
                x = Integer.parseInt(String.valueOf(position.charAt(0)));
                y = Integer.parseInt(String.valueOf(position.charAt(1)))+1;

                if(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))!=null) {
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
            }
            if (turnLeft){
                x = Integer.parseInt(String.valueOf(position.charAt(0)) );
                y = Integer.parseInt(String.valueOf(position.charAt(1)))-1;

                if(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))!=null) {
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
            }
            if (turnTop){
                x = Integer.parseInt(String.valueOf(position.charAt(0)))-1;
                y = Integer.parseInt(String.valueOf(position.charAt(1)));

                if(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))!=null) {
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
            }
            if (turnDown){
                x = Integer.parseInt(String.valueOf(position.charAt(0)))+1;
                y = Integer.parseInt(String.valueOf(position.charAt(1)));

                if(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))!=null) {
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
            }
        } else {

            //Check if we can flip the card at the right of this card
            if (positionNumeric_y < 3) {
                turnRight = this.checkRight(positionNumeric_x, positionNumeric_y);
            }

            //Check if we can flip the card at the left of this card
            if (positionNumeric_y > 1) {
                turnLeft = this.checkLeft(positionNumeric_x, positionNumeric_y);
            }

            //Check if we can flip the card above this card
            if (positionNumeric_x > 1) {
                turnTop = this.checkTop(positionNumeric_x, positionNumeric_y);
            }

            //Check if we can flip the card at the bottom of this card
            if (positionNumeric_x < 3) {
                turnDown = this.checkDown(positionNumeric_x, positionNumeric_y);
            }

            //Check if we can flip a card around this card
            if (turnRight
                    || turnLeft
                    || turnDown
                    || turnTop
            ) {
                if (turnRight) {
                    x = Integer.parseInt(String.valueOf(position.charAt(0)));
                    y = Integer.parseInt(String.valueOf(position.charAt(1))) + 1;

                    //Combo rule : called again Flip rule
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
                if (turnLeft) {
                    x = Integer.parseInt(String.valueOf(position.charAt(0)));
                    y = Integer.parseInt(String.valueOf(position.charAt(1))) - 1;

                    //Combo rule : called again Flip rule
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
                if (turnTop) {
                    x = Integer.parseInt(String.valueOf(position.charAt(0))) - 1;
                    y = Integer.parseInt(String.valueOf(position.charAt(1)));

                    //Combo rule : called again Flip rule
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
                if (turnDown) {
                    x = Integer.parseInt(String.valueOf(position.charAt(0))) + 1;
                    y = Integer.parseInt(String.valueOf(position.charAt(1)));

                    //Combo rule : called again Flip rule
                    if (!(visitedCard.contains(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y)))))) {
                        visitedCard.add(this.client.getFF8CardName(this.game.getUUID()).get(Integer.toString(x).concat(Integer.toString(y))));
                        flipRule(Integer.toString(x).concat(Integer.toString(y)), visitedCard);
                    }

                    //Send update to the server to spread it to all client
                    this.client.getMainServer().setFF8CardOwner(this.game.getUUID(), Integer.toString(x) + Integer.toString((y)), this.client.getClientPseudo());
                }
            }
        }
    }

    /*
    @param int positionX
    @param int positionY

    @throws RemoteException

    These methods check around a specific card if we can flip some card
     */

    public boolean checkRight(int positionX,int positionY) throws RemoteException {
        int cardValueRight=this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
        int x = positionX;
        int y = positionY+1;
        String key=Integer.toString(x).concat(Integer.toString(y));

        //Check if the card at the right exist
        if (this.client.getFF8CardLeft(this.game.getUUID()).get(key)!=null){
            int cardValueLeft=this.client.getFF8CardLeft(this.game.getUUID()).get(key);

            //Check if the card at the right is not my card
            if (!this.client.getFF8CardOwner(this.game.getUUID()).get(key).equals(this.client.getClientPseudo())){

                //Check if we can flip this card
                if (cardValueRight > cardValueLeft){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }
    public boolean checkTop(int positionX,int positionY) throws RemoteException {
        int cardValueTop=this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
        int x = positionX-1;
        int y = positionY;
        String key=Integer.toString(x).concat(Integer.toString(y));

        //Check if the card at the top exist
        if (this.client.getFF8CardDown(this.game.getUUID()).get(key)!=null){
            int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(key);

            //Check if the card at the top is not my card
            if (!this.client.getFF8CardOwner(this.game.getUUID()).get(key).equals(this.client.getClientPseudo())){

                //Check if we can flip this card
                if (cardValueTop > cardValueDown){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }
    public boolean checkLeft(int positionX,int positionY) throws RemoteException {
        int cardValueLeft=this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
        int x = positionX;
        int y = positionY-1;
        String key=Integer.toString(x).concat(Integer.toString(y));

        //Check if the card at the left exist
        if (this.client.getFF8CardRight(this.game.getUUID()).get(key)!=null){
            int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(key);

            //Check if the card at the left is not my card
            if (!this.client.getFF8CardOwner(this.game.getUUID()).get(key).equals(this.client.getClientPseudo())){

                //Check if we can flip this card
                if (cardValueLeft > cardValueRight){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }
    public boolean checkDown(int positionX, int positionY) throws RemoteException {
        int cardValueDown=this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
        int x = positionX+1;
        int y = positionY;
        String key = Integer.toString(x).concat(Integer.toString(y));

        //Check if the card at the bottom exist
        if (this.client.getFF8CardUp(this.game.getUUID()).get(key)!=null){
            int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(key);

            //Check if the card at the bottom is not my card
            if(!this.client.getFF8CardOwner(this.game.getUUID()).get(key).equals(this.client.getClientPseudo())) {

                //Check if we can flip this card
                if (cardValueDown > cardValueTop){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }
    public boolean checkSame(int positionX, int positionY) throws RemoteException {
        if(positionX == 2 && positionY == 2) {
            int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
            int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
            int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
            int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
            String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
            String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
            String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));
            String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

            //Check if cards around exist
            if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                    && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null)
                    || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                    && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                    || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                    && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                    || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                    || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                    || (this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
            ) {
                if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ) {
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop == cardAroundValueDown && cardValueRight == cardAroundValueLeft)
                                || (cardValueTop == cardAroundValueDown && cardValueDown == cardAroundValueTop)
                                || (cardValueTop == cardAroundValueDown && cardValueLeft == cardAroundValueRight)
                                || (cardValueRight == cardAroundValueLeft && cardValueDown == cardAroundValueTop)
                                || (cardValueRight == cardAroundValueLeft && cardValueLeft == cardAroundValueRight)
                                || (cardValueDown == cardAroundValueTop && cardValueLeft == cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueRight == cardAroundValueLeft && cardValueDown == cardAroundValueTop)
                                || (cardValueRight == cardAroundValueLeft && cardValueLeft == cardAroundValueRight)
                                || (cardValueDown == cardAroundValueTop && cardValueLeft == cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop == cardAroundValueDown && cardValueDown == cardAroundValueTop)
                                || (cardValueTop == cardAroundValueDown && cardValueLeft == cardAroundValueRight)
                                || (cardValueDown == cardAroundValueTop && cardValueLeft == cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop == cardAroundValueDown && cardValueRight == cardAroundValueLeft)
                                || (cardValueTop == cardAroundValueDown && cardValueLeft == cardAroundValueRight)
                                || (cardValueRight == cardAroundValueLeft && cardValueLeft == cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop == cardAroundValueDown && cardValueRight == cardAroundValueLeft)
                                || (cardValueTop == cardAroundValueDown && cardValueDown == cardAroundValueTop)
                                || (cardValueRight == cardAroundValueLeft && cardValueDown == cardAroundValueTop)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueDown == cardAroundValueTop && cardValueLeft == cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueRight == cardAroundValueLeft && cardValueLeft == cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                ){
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueRight == cardAroundValueLeft && cardValueDown == cardAroundValueTop)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop == cardAroundValueDown && cardValueLeft == cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop == cardAroundValueDown && cardValueDown == cardAroundValueTop)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop == cardAroundValueDown && cardValueRight == cardAroundValueLeft)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return false;
        }else if(positionX == 1){
            if(positionY == 1){
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueRight == cardAroundValueLeft && cardValueDown == cardAroundValueTop)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }else if(positionY == 3){
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null)
                ) {
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueDown == cardAroundValueTop && cardValueLeft == cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }else{
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null)
                        || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                        || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ) {
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight == cardAroundValueLeft && cardValueDown == cardAroundValueTop)
                                    || (cardValueRight == cardAroundValueLeft && cardValueLeft == cardAroundValueRight)
                                    || (cardValueDown == cardAroundValueTop && cardValueLeft == cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                    ){
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight == cardAroundValueLeft && cardValueDown == cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueDown == cardAroundValueTop && cardValueLeft == cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight == cardAroundValueLeft && cardValueLeft == cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
        }else if(positionX == 3){
            if(positionY == 1){
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop == cardAroundValueDown && cardValueRight == cardAroundValueLeft)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }else if(positionY == 3){
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));

                //Check if cards around exist
                if ((this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                ) {
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop == cardAroundValueDown && cardValueLeft == cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }else{
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));

                //Check if cards around exist
                if ((this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                        || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                        || (this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ) {
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop == cardAroundValueDown && cardValueRight == cardAroundValueLeft)
                                    || (cardValueTop == cardAroundValueDown && cardValueLeft == cardAroundValueRight)
                                    || (cardValueRight == cardAroundValueLeft && cardValueLeft == cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight == cardAroundValueLeft && cardValueLeft == cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop == cardAroundValueDown && cardValueLeft == cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop == cardAroundValueDown && cardValueRight == cardAroundValueLeft)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
        }else{
            if(positionY == 1){
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                        || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                        || (this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ) {
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop == cardAroundValueDown && cardValueRight == cardAroundValueLeft)
                                    || (cardValueTop == cardAroundValueDown && cardValueDown == cardAroundValueTop)
                                    || (cardValueRight == cardAroundValueLeft && cardValueDown == cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                    ){
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight == cardAroundValueLeft && cardValueDown == cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop == cardAroundValueDown && cardValueDown == cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop == cardAroundValueDown && cardValueRight == cardAroundValueLeft)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                return false;
            }else if(positionY == 3){
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null)
                        || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                        || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                ) {
                    if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ) {
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop == cardAroundValueDown && cardValueDown == cardAroundValueTop)
                                    || (cardValueTop == cardAroundValueDown && cardValueLeft == cardAroundValueRight)
                                    || (cardValueDown == cardAroundValueTop && cardValueLeft == cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop == cardAroundValueDown && cardValueDown == cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueDown == cardAroundValueTop && cardValueLeft == cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop == cardAroundValueDown && cardValueLeft == cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    public boolean checkPlus(int positionX, int positionY) throws RemoteException {
        if(positionX == 2 && positionY == 2) {
            int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
            int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
            int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
            int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
            String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
            String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
            String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));
            String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

            //Check if cards around exist
            if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                    && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null)
                    || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                    && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                    || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                    && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                    || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                    || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                    || (this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
            ) {
                if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ) {
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop + cardAroundValueDown == cardValueRight + cardAroundValueLeft)
                                || (cardValueTop + cardAroundValueDown == cardValueDown + cardAroundValueTop)
                                || (cardValueTop + cardAroundValueDown == cardValueLeft + cardAroundValueRight)
                                || (cardValueRight + cardAroundValueLeft == cardValueDown + cardAroundValueTop)
                                || (cardValueRight + cardAroundValueLeft == cardValueLeft + cardAroundValueRight)
                                || (cardValueDown + cardAroundValueTop == cardValueLeft + cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueRight + cardAroundValueLeft == cardValueDown + cardAroundValueTop)
                                || (cardValueRight + cardAroundValueLeft == cardValueLeft + cardAroundValueRight)
                                || (cardValueDown + cardAroundValueTop == cardValueLeft + cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop + cardAroundValueDown == cardValueDown + cardAroundValueTop)
                                || (cardValueTop + cardAroundValueDown == cardValueLeft + cardAroundValueRight)
                                || (cardValueDown + cardAroundValueTop == cardValueLeft + cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop + cardAroundValueDown == cardValueRight + cardAroundValueLeft)
                                || (cardValueTop + cardAroundValueDown == cardValueLeft + cardAroundValueRight)
                                || (cardValueRight + cardAroundValueLeft == cardValueLeft + cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                            || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop + cardAroundValueDown == cardValueRight + cardAroundValueLeft)
                                || (cardValueTop + cardAroundValueDown == cardValueDown + cardAroundValueTop)
                                || (cardValueRight + cardAroundValueLeft == cardValueDown + cardAroundValueTop)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueDown + cardAroundValueTop == cardValueLeft + cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueRight + cardAroundValueLeft == cardValueLeft + cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                ){
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueRight + cardAroundValueLeft == cardValueDown + cardAroundValueTop)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop + cardAroundValueDown == cardValueLeft + cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop + cardAroundValueDown == cardValueDown + cardAroundValueTop)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else if(this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                ){
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop + cardAroundValueDown == cardValueRight + cardAroundValueLeft)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return false;
        }else if(positionX == 1){
            if(positionY == 1){
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueRight + cardAroundValueLeft == cardValueDown + cardAroundValueTop)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }else if(positionY == 3){
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null)
                ) {
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueDown + cardAroundValueTop == cardValueLeft + cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }else{
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null)
                        || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                        || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ) {
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight + cardAroundValueLeft == cardValueDown + cardAroundValueTop)
                                    || (cardValueRight + cardAroundValueLeft == cardValueLeft + cardAroundValueRight)
                                    || (cardValueDown + cardAroundValueTop == cardValueLeft + cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                    ){
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight + cardAroundValueLeft == cardValueDown + cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueDown + cardAroundValueTop == cardValueLeft + cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight + cardAroundValueLeft == cardValueLeft + cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
        }else if(positionX == 3){
            if(positionY == 1){
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop + cardAroundValueDown == cardValueRight + cardAroundValueLeft)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }else if(positionY == 3){
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));

                //Check if cards around exist
                if ((this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                ) {
                    int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                    int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                    //Check if cards around are not my card
                    if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                            && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                    ) {

                        //Check if we can flip this card
                        if ((cardValueTop + cardAroundValueDown == cardValueLeft + cardAroundValueRight)
                        ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }else{
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));

                //Check if cards around exist
                if ((this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                        || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                        || (this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ) {
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop + cardAroundValueDown == cardValueRight + cardAroundValueLeft)
                                    || (cardValueTop + cardAroundValueDown == cardValueLeft + cardAroundValueRight)
                                    || (cardValueRight + cardAroundValueLeft == cardValueLeft + cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight + cardAroundValueLeft == cardValueLeft + cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop + cardAroundValueDown == cardValueLeft + cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop + cardAroundValueDown == cardValueRight + cardAroundValueLeft)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
        }else{
            if(positionY == 1){
                int cardValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyR = Integer.toString(positionX).concat(Integer.toString(positionY + 1));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                        || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                        || (this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                        && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null)
                ) {
                    if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ) {
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop + cardAroundValueDown == cardValueRight + cardAroundValueLeft)
                                    || (cardValueTop + cardAroundValueDown == cardValueDown + cardAroundValueTop)
                                    || (cardValueRight + cardAroundValueLeft == cardValueDown + cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                    ){
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueRight + cardAroundValueLeft == cardValueDown + cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop + cardAroundValueDown == cardValueDown + cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardLeft(this.game.getUUID()).get(keyR) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(keyR);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyR).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop + cardAroundValueDown == cardValueRight + cardAroundValueLeft)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                return false;
            }else if(positionY == 3){
                int cardValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueLeft = this.client.getFF8CardLeft(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                int cardValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(Integer.toString(positionX).concat(Integer.toString(positionY)));
                String keyT = Integer.toString(positionX - 1).concat(Integer.toString(positionY));
                String keyL = Integer.toString(positionX).concat(Integer.toString(positionY - 1));
                String keyD = Integer.toString(positionX + 1).concat(Integer.toString(positionY));

                //Check if cards around exist
                if ((this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null)
                        || (this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                        || (this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                        && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null)
                ) {
                    if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ) {
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                                || (!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop + cardAroundValueDown == cardValueDown + cardAroundValueTop)
                                    || (cardValueTop + cardAroundValueDown == cardValueLeft + cardAroundValueRight)
                                    || (cardValueDown + cardAroundValueTop == cardValueLeft + cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                    ){
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop + cardAroundValueDown == cardValueDown + cardAroundValueTop)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardUp(this.game.getUUID()).get(keyD) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueTop = this.client.getFF8CardUp(this.game.getUUID()).get(keyD);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyD).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueDown + cardAroundValueTop == cardValueLeft + cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }else if(this.client.getFF8CardDown(this.game.getUUID()).get(keyT) != null
                            && this.client.getFF8CardRight(this.game.getUUID()).get(keyL) != null
                    ){
                        int cardAroundValueRight = this.client.getFF8CardRight(this.game.getUUID()).get(keyL);
                        int cardAroundValueDown = this.client.getFF8CardDown(this.game.getUUID()).get(keyT);

                        //Check if cards around are not my card
                        if ((!this.client.getFF8CardOwner(this.game.getUUID()).get(keyT).equals(this.client.getClientPseudo())
                                && !this.client.getFF8CardOwner(this.game.getUUID()).get(keyL).equals(this.client.getClientPseudo()))
                        ) {

                            //Check if we can flip this card
                            if ((cardValueTop + cardAroundValueDown == cardValueLeft + cardAroundValueRight)
                            ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

}
