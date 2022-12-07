package fr.playcards.server;

//Import part
import fr.playcards.cardgame.CardGame;
import fr.playcards.cardgame.card.Card;
import fr.playcards.client.IClient;
import fr.playcards.room.IRoom;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/*
@author Yotlan LE CROM
@author Endy YU

This interface have for goal to extends Serializable and Remote interface to able viewing and sharing this object.
 */

public interface IServer extends Serializable, Remote {

    /*
    @param CardGame game
    @param IClient client

    @throws RemoteException

    This method is called by PlayCardsController and create a room in the server who spread the update to all clients.
     */

    void createRoom(CardGame game, IClient client) throws RemoteException;

    /*
    @throws RemoteException

    This method is called by Client and update the room list.
     */

    List<IRoom> getObservableRoomList() throws RemoteException;

    /*
    @throws RemoteException

    This method is a getter method who return IServer's name.
     */

    String getName() throws RemoteException;

    /*
    @throws RemoteException

    This method is called by Server when create a room. So we spread it to all clients.
     */

    void refreshRoom() throws RemoteException;

    /*
    @param String UUID

    @throws RemoteException

    This method is called by different CardGameController and initialize the turn of the UUID game to the player 1, so
    to the first player who join the room.
     */

    void initTurn(String UUID) throws RemoteException;

    /*
    @param String UUID

    @throws RemoteException

    This method is called by different CardGameController and switch turn from the player 1 to the player 2 or from the
    player 2 to the player 1 in the UUID game.
     */

    void switchTurn(String UUID) throws RemoteException;

    /*
    @param String UUID

    @throws RemoteException

    This method is called by different CardGameController and return the current player's turn (1 or 2)
     */

    Integer getTurn(String UUID) throws RemoteException;

    //Display methods
    /*
    @param Card card
    @param String UUID
    @param IClient client

    @throws RemoteException

    Theses methods are called by different CardGameController and there behavior are to add a card and all his
    informations into some map. Then we launch the refresh method to spread all the
    displaying card to all clients who're in the UUID game !
     */

    void displayCard11(Card card, String UUID,IClient client) throws RemoteException;
    void displayCard21(Card card, String UUID,IClient client) throws RemoteException;
    void displayCard31(Card card, String UUID,IClient client) throws RemoteException;
    void displayCard12(Card card, String UUID,IClient client) throws RemoteException;
    void displayCard22(Card card, String UUID,IClient client) throws RemoteException;
    void displayCard32(Card card, String UUID,IClient client) throws RemoteException;
    void displayCard13(Card card, String UUID,IClient client) throws RemoteException;
    void displayCard23(Card card, String UUID,IClient client) throws RemoteException;
    void displayCard33(Card card, String UUID,IClient client) throws RemoteException;

    /*
    @param String UUID
    @param IClient client

    @throws RemoteException

    This method is called by different CardGameController and initialize a map with a key who's the UUID game and values
    are a list of all client who're in the UUID game.
     */

    void initFF8GameClientList(String UUID, IClient client) throws RemoteException;

    /*
    @param String UUID

    @throws RemoteException

    This method is called by different CardGameController and return a list of all clients who're in the UUID game.
     */

    List<IClient> getFF8GameClientList(String UUID) throws RemoteException;

    //Getter card's information method
    /*
    @throws RemoteException

    These methods are called by Client and return maps with all the FF8Card Name, Level, Up, Right, Down, Left and Owner values.
     */

    Map<String,String> getFF8CardName() throws RemoteException;
    Map<String,Integer> getFF8CardLevel() throws RemoteException;
    Map<String,Integer> getFF8CardUp() throws RemoteException;
    Map<String,Integer> getFF8CardRight() throws RemoteException;
    Map<String,Integer> getFF8CardDown() throws RemoteException;
    Map<String,Integer> getFF8CardLeft() throws RemoteException;
    Map<String,String> getFF8CardOwner() throws RemoteException;

    /*
    @param String UUID
    @param String position
    @param String newOwner

    @throws RemoteException

    This method is called by different CardGameController and replace the old owner of a card at the position position
    by his new owner.
     */

    boolean setFF8CardOwner(String UUID,String position, String newOwner) throws RemoteException;

}
