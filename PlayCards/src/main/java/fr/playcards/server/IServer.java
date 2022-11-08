package fr.playcards.server;

import fr.playcards.cardgame.CardGame;
import fr.playcards.client.IClient;
import fr.playcards.room.IRoom;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServer extends Serializable, Remote {
    public void createRoom(CardGame game, IClient client) throws RemoteException;

    public List<IRoom> getObservableRoomList() throws RemoteException;

    public String getName() throws RemoteException;
    public void refresh() throws RemoteException;
}
