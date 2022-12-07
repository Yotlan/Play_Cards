package fr.playcards.room;

//Import part
import fr.playcards.cardgame.CardGame;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
@author Yotlan LE CROM

This interface have for goal to extends Remote interface to be able to sharing this object.
 */

public interface IRoom extends Remote {

    /*
    @param String pseudo

    @throws RemoteException

    This method have for goal to connect the client with the name pseudo to the current room, and so to the current card
    game.
     */

    void connect(String pseudo) throws RemoteException;

    //Getter room's information method
    /*
    @throws RemoteException

    These methods return room's information.
     */

    String getRoom() throws RemoteException;
    String getCardGame() throws RemoteException;
    Integer getNbPlayer() throws RemoteException;
    CardGame getCurrentCardGame() throws RemoteException;

}
