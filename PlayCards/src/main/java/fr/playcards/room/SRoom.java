package fr.playcards.room;

import fr.playcards.cardgame.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SRoom extends UnicastRemoteObject implements IRoom {

    public SRoom(CardGame cardGame) throws RemoteException {
        System.out.println(cardGame);
    }

    public void connect(String pseudo) throws RemoteException {
        System.out.println(pseudo);
    }

}
