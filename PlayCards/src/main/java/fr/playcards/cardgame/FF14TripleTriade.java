package fr.playcards.cardgame;

//Import part
import fr.playcards.cardgame.card.Card;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

//TODO : Complete this java file

public class FF14TripleTriade extends UnicastRemoteObject implements CardGame {
    private Integer MAX_PLAYER;
    public String UUID;
    public CardGameController controller;
    public List<Card> Player1_Card = new ArrayList<>();
    public List<Card> Player2_Card = new ArrayList<>();
    public String Player1_Pseudo = "";
    public String Player2_Pseudo = "";

    public FF14TripleTriade(String UUID) throws IOException {
        MAX_PLAYER = 2;
        this.UUID = UUID;
    }

    public String getName() {
        return "Triple Triade - Final Fantasy 14";
    }
    public Integer getMaxPlayer() {
        return MAX_PLAYER;
    }
    public String getUUID() throws RemoteException{
        return this.UUID;
    }
    public CardGameController getController() throws RemoteException{
        return this.controller;
    }
    public void setController(CardGameController controller) throws RemoteException{
        this.controller = controller;
    }
    public List<Card> getPlayer1Card() throws RemoteException{
        return this.Player1_Card;
    }
    public void removePlayer1Card(int index) throws RemoteException {
        this.Player1_Card.remove(index);
    }
    public List<Card> getPlayer2Card() throws RemoteException{
        return this.Player2_Card;
    }
    public void removePlayer2Card(int index) throws RemoteException {
        this.Player1_Card.remove(index);
    }
    public String getPlayer1Pseudo() throws RemoteException{
        return Player1_Pseudo;
    }
    public void setPlayer1Pseudo(String pseudo) throws RemoteException{
        this.Player1_Pseudo = pseudo;
    }
    public String getPlayer2Pseudo() throws RemoteException{
        return this.Player2_Pseudo;
    }
    public void setPlayer2Pseudo(String pseudo) throws RemoteException{
        this.Player2_Pseudo = pseudo;
    }

}
