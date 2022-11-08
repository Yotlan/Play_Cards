package fr.playcards.client;

import fr.playcards.server.IServer;
import fr.playcards.room.IRoom;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Client implements IClient {

    public List<IRoom> observableRoomList = new ArrayList<>();
    public IServer mainServer;

    public Client() {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            mainServer = (IServer) registry.lookup("play-cards/1099/connecting");
            this.observableRoomList = mainServer.getObservableRoomList();
        } catch (Exception e) {
            System.out.println("Client Constructor Error : "+e);
        }
    }

    public void refresh() {
        try {
            this.observableRoomList = this.mainServer.getObservableRoomList();
        } catch (Exception e) {
            System.out.println("Client refresh method Error : "+e);
        }
    }

    public List<IRoom> getObservableRoomList() {
        refresh();
        return this.observableRoomList;
    }

}
