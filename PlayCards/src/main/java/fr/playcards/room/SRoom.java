package fr.playcards.room;

import fr.playcards.cardgame.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class SRoom extends UnicastRemoteObject implements IRoom {

    public SimpleStringProperty room;
    public SimpleStringProperty cardgame;
    public SimpleIntegerProperty nbplayer;

    private CardGame currentCardGame;

    public SRoom(CardGame cardGame) throws RemoteException {
        room = new SimpleStringProperty(UUID.randomUUID().toString());
        cardgame = new SimpleStringProperty(cardGame.toString());
        nbplayer = new SimpleIntegerProperty(0);
        currentCardGame = cardGame;
    }

    public void connect(String pseudo) throws RemoteException {
        System.out.println(pseudo + " join the game !");
        nbplayer.set(nbplayer.get()+1);
    }

    public String getRoom() throws RemoteException {
        return room.get();
    }

    public String getCardgame() throws RemoteException {
        return cardgame.get();
    }

    public Integer getNbplayer() throws RemoteException {
        return nbplayer.get();
    }

    public CardGame getCurrentCardGame() throws RemoteException {
        return currentCardGame;
    }

}
