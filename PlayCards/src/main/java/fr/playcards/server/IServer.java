package fr.playcards.server;

import fr.playcards.cardgame.CardGame;
import fr.playcards.cardgame.card.Card;
import fr.playcards.client.IClient;
import fr.playcards.room.IRoom;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IServer extends Serializable, Remote {
    public void createRoom(CardGame game, IClient client) throws RemoteException;

    public List<IRoom> getObservableRoomList() throws RemoteException;

    public String getName() throws RemoteException;
    public void refreshRoom() throws RemoteException;

    public void displayCard11(Card card, String UUID,IClient client) throws RemoteException;
    public void displayCard21(Card card, String UUID,IClient client) throws RemoteException;
    public void displayCard31(Card card, String UUID,IClient client) throws RemoteException;
    public void displayCard12(Card card, String UUID,IClient client) throws RemoteException;
    public void displayCard22(Card card, String UUID,IClient client) throws RemoteException;
    public void displayCard32(Card card, String UUID,IClient client) throws RemoteException;
    public void displayCard13(Card card, String UUID,IClient client) throws RemoteException;
    public void displayCard23(Card card, String UUID,IClient client) throws RemoteException;
    public void displayCard33(Card card, String UUID,IClient client) throws RemoteException;

    public Map<String,Card> getFF8Card() throws RemoteException;
}
