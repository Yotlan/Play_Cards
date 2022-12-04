package fr.playcards.client;

import fr.playcards.cardgame.card.Card;
import fr.playcards.room.IRoom;
import fr.playcards.server.IServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IClient extends Serializable {

    public void refreshRoom();
    public List<IRoom> getObservableRoomList();

    public IServer getMainServer();

    public void refreshDisplayCard();

    public String getClientPseudo();

    public void setClientPseudo(String pseudo);

    public Map<String, String> getFF8Card(String UUID);

    public Map<String,Integer> getFF8CardUp(String UUID) throws RemoteException;
    public Map<String,Integer> getFF8CardRight(String UUID) throws RemoteException;
    public Map<String,Integer> getFF8CardDown(String UUID) throws RemoteException;
    public Map<String,Integer> getFF8CardLeft(String UUID) throws RemoteException;
    public Map<String,String> getFF8CardOwner(String UUID) throws RemoteException;

}
