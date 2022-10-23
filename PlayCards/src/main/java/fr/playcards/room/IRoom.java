package fr.playcards.room;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote {

    public void connect(String pseudo) throws RemoteException;

}
