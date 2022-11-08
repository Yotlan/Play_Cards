package fr.playcards.server;

import fr.playcards.cardgame.CardGame;
import fr.playcards.client.IClient;
import fr.playcards.room.IRoom;
import fr.playcards.room.SRoom;
import fr.playcards.server.IServer;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements IServer {

    public List<IRoom> observableRoomList = new ArrayList<>();
    public List<IClient> clientList = new ArrayList<>();
    public String name;

    public Server(String name) throws RemoteException{
        this.name = name;
    }
    public void createRoom(CardGame game,IClient client) throws RemoteException {
        observableRoomList.add(new SRoom(game));
        clientList.add(client);
        for(IClient clt : clientList){
            clt.refresh();
        }
    }
    public List<IRoom> getObservableRoomList() throws RemoteException {
        return this.observableRoomList;
    }

    public String getName() throws RemoteException {
        return this.name;
    }

    public void refresh() throws RemoteException {
        for(IClient clt : clientList){
            clt.refresh();
        }
    }
}
