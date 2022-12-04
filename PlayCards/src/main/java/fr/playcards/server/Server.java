package fr.playcards.server;

import fr.playcards.cardgame.CardGame;
import fr.playcards.cardgame.card.Card;
import fr.playcards.client.IClient;
import fr.playcards.room.IRoom;
import fr.playcards.room.SRoom;
import fr.playcards.server.IServer;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends UnicastRemoteObject implements IServer {

    public List<IRoom> observableRoomList = new ArrayList<>();
    public List<IClient> clientList = new ArrayList<>();
    public String name;

    public Map<String,Card> FF8Card = new HashMap<>();
    public Map<String,List<IClient>> FF8GameClientList = new HashMap<>();

    public Map<String,Integer> FF8GameClientTurn = new HashMap<>();

    public Server(String name) throws RemoteException{
        this.name = name;
    }
    public void createRoom(CardGame game,IClient client) throws RemoteException {
        observableRoomList.add(new SRoom(game));
        clientList.add(client);
        for(IClient clt : clientList){
            clt.refreshRoom();
        }
    }
    public List<IRoom> getObservableRoomList() throws RemoteException {
        return this.observableRoomList;
    }

    public String getName() throws RemoteException {
        return this.name;
    }

    public void refreshRoom() throws RemoteException {
        for(IClient clt : clientList){
            clt.refreshRoom();
        }
    }

    public void initTurn(String UUID) throws RemoteException {
        if(!FF8GameClientTurn.containsKey(UUID)) {
            FF8GameClientTurn.put(UUID,1);
        }
    }
    public void switchTurn(String UUID) throws RemoteException {
        if(FF8GameClientTurn.containsKey(UUID)){
            if(FF8GameClientTurn.get(UUID) == 1){
                FF8GameClientTurn.replace(UUID,2);
            }else{
                FF8GameClientTurn.replace(UUID,1);
            }
        }
    }

    public Integer getTurn(String UUID) throws RemoteException {
        return this.FF8GameClientTurn.get(UUID);
    }

    public void displayCard11(Card card, String UUID, IClient client) throws RemoteException{
        FF8Card.put(UUID+"#11",card);
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public void displayCard21(Card card, String UUID, IClient client) throws RemoteException{
        FF8Card.put(UUID+"#21",card);
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public void displayCard31(Card card, String UUID, IClient client) throws RemoteException{
        FF8Card.put(UUID+"#31",card);
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public void displayCard12(Card card, String UUID, IClient client) throws RemoteException{
        FF8Card.put(UUID+"#12",card);
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public void displayCard22(Card card, String UUID, IClient client) throws RemoteException{
        FF8Card.put(UUID+"#22",card);
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public void displayCard32(Card card, String UUID, IClient client) throws RemoteException{
        FF8Card.put(UUID+"#32",card);
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public void displayCard13(Card card, String UUID, IClient client) throws RemoteException{
        FF8Card.put(UUID+"#13",card);
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public void displayCard23(Card card, String UUID, IClient client) throws RemoteException{
        FF8Card.put(UUID+"#23",card);
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public void displayCard33(Card card, String UUID, IClient client) throws RemoteException{
        FF8Card.put(UUID+"#33",card);
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public Map<String,Card> getFF8Card() throws RemoteException{
        System.out.println(FF8Card);
        return this.FF8Card;
    }
}
