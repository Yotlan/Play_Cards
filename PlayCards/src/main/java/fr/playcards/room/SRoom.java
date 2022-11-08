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
        room = new SimpleStringProperty(cardGame.getUUID());
        cardgame = new SimpleStringProperty(cardGame.getName());
        nbplayer = new SimpleIntegerProperty(0);
        currentCardGame = cardGame;
    }

    public synchronized void connect(String pseudo) throws RemoteException {
        System.out.println(pseudo + " join the game !");
        nbplayer.set(nbplayer.get()+1);
        if(nbplayer.get()==1) {
            currentCardGame.setPlayer1Pseudo(pseudo);
        }else{
            currentCardGame.setPlayer2Pseudo(pseudo);
        }
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
