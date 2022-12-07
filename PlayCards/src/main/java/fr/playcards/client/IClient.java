package fr.playcards.client;

//Import part
import fr.playcards.room.IRoom;
import fr.playcards.server.IServer;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/*
@author Yotlan LE CROM
@author Endy YU

This interface have for goal to extends Serializable interface, and so to viewing client element in different frame.
 */

public interface IClient extends Serializable {

    /*
    This method is called by PlayCardsController and send to the controller updated room.
     */

    void refreshRoom();

    //Getter client's information method

    List<IRoom> getObservableRoomList();
    IServer getMainServer();

    /*
    This method is called by different CardGameController and send to the controller updated displayed cards.
     */

    void refreshDisplayCard();

    //Getter/Setter client pseudo methods
    /*
    @param String pseudo

    These methods can get and set client pseudo.
     */

    String getClientPseudo();
    void setClientPseudo(String pseudo);

    //Getter card's information method
    /*
    @param String UUID

    These methods return all card's information.
     */

    Map<String,String> getFF8CardName(String UUID);
    Map<String,Integer> getFF8CardLevel(String UUID) throws RemoteException;
    Map<String,Integer> getFF8CardUp(String UUID) throws RemoteException;
    Map<String,Integer> getFF8CardRight(String UUID) throws RemoteException;
    Map<String,Integer> getFF8CardDown(String UUID) throws RemoteException;
    Map<String,Integer> getFF8CardLeft(String UUID) throws RemoteException;
    Map<String,String> getFF8CardOwner(String UUID) throws RemoteException;

}
