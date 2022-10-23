package fr.playcards.room;

import fr.playcards.cardgame.CardGame;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote {

    public void connect(String pseudo) throws RemoteException;

    public String getRoom() throws RemoteException;

    public String getCardgame() throws RemoteException;

    public Integer getNbplayer() throws RemoteException;

    public CardGame getCurrentCardGame() throws RemoteException;

}
