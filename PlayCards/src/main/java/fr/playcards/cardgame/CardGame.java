package fr.playcards.cardgame;

import fr.playcards.cardgame.card.Card;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CardGame extends Serializable, Remote{

    public String getName() throws RemoteException;

    public Integer getMaxPlayer() throws RemoteException;

    public String getUUID() throws RemoteException;

    public CardGameController getController() throws RemoteException;
    public void setController(CardGameController controller) throws RemoteException;

    public List<Card> getPlayer1Card() throws RemoteException;

    public List<Card> getPlayer2Card() throws RemoteException;

    public String getPlayer1Pseudo() throws RemoteException;

    public void setPlayer1Pseudo(String pseudo) throws RemoteException;

    public String getPlayer2Pseudo() throws RemoteException;
    public void setPlayer2Pseudo(String pseudo) throws RemoteException;

}
