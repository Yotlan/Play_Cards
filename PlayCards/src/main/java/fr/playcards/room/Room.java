package fr.playcards.room;

//Import part
import fr.playcards.cardgame.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Room extends UnicastRemoteObject implements IRoom {

    public SimpleStringProperty room;
    public SimpleStringProperty cardGame;
    public SimpleIntegerProperty nbPlayer;
    private CardGame currentCardGame;

    public Room(CardGame cardGame) throws RemoteException {
        room = new SimpleStringProperty(cardGame.getUUID());
        this.cardGame = new SimpleStringProperty(cardGame.getName());
        nbPlayer = new SimpleIntegerProperty(0);
        currentCardGame = cardGame;
    }

    public void connect(String pseudo) throws RemoteException {
        nbPlayer.set(nbPlayer.get()+1);
        if(nbPlayer.get()==1) {
            currentCardGame.setPlayer1Pseudo(pseudo);
        }else{
            currentCardGame.setPlayer2Pseudo(pseudo);
        }
    }

    public String getRoom() throws RemoteException {
        return room.get();
    }
    public String getCardGame() throws RemoteException {
        return cardGame.get();
    }
    public Integer getNbPlayer() throws RemoteException {
        return nbPlayer.get();
    }
    public CardGame getCurrentCardGame() throws RemoteException {
        return currentCardGame;
    }

}
