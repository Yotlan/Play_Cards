package fr.playcards.server;

import fr.playcards.StartServer;
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
    public Map<String,String> FF8CardName = new HashMap<>();
    public Map<String,Integer> FF8CardUp = new HashMap<>();
    public Map<String,Integer> FF8CardRight = new HashMap<>();
    public Map<String,Integer> FF8CardDown = new HashMap<>();
    public Map<String,Integer> FF8CardLeft = new HashMap<>();
    public Map<String,String> FF8CardOwner = new HashMap<>();

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
        FF8CardName.put(UUID+"#11", card.getName());
        FF8CardUp.put(UUID+"#11", card.getUp());
        FF8CardRight.put(UUID+"#11", card.getRight());
        FF8CardDown.put(UUID+"#11", card.getDown());
        FF8CardLeft.put(UUID+"#11", card.getLeft());
        FF8CardOwner.put(UUID+"#11", card.getOwner().getClientPseudo());
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
        FF8CardName.put(UUID+"#21", card.getName());
        FF8CardUp.put(UUID+"#21", card.getUp());
        FF8CardRight.put(UUID+"#21", card.getRight());
        FF8CardDown.put(UUID+"#21", card.getDown());
        FF8CardLeft.put(UUID+"#21", card.getLeft());
        FF8CardOwner.put(UUID+"#21", card.getOwner().getClientPseudo());
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
        FF8CardName.put(UUID+"#31", card.getName());
        FF8CardUp.put(UUID+"#31", card.getUp());
        FF8CardRight.put(UUID+"#31", card.getRight());
        FF8CardDown.put(UUID+"#31", card.getDown());
        FF8CardLeft.put(UUID+"#31", card.getLeft());
        FF8CardOwner.put(UUID+"#31", card.getOwner().getClientPseudo());
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
        FF8CardName.put(UUID+"#12", card.getName());
        FF8CardUp.put(UUID+"#12", card.getUp());
        FF8CardRight.put(UUID+"#12", card.getRight());
        FF8CardDown.put(UUID+"#12", card.getDown());
        FF8CardLeft.put(UUID+"#12", card.getLeft());
        FF8CardOwner.put(UUID+"#12", card.getOwner().getClientPseudo());
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
        FF8CardName.put(UUID+"#22", card.getName());
        FF8CardUp.put(UUID+"#22", card.getUp());
        FF8CardRight.put(UUID+"#22", card.getRight());
        FF8CardDown.put(UUID+"#22", card.getDown());
        FF8CardLeft.put(UUID+"#22", card.getLeft());
        FF8CardOwner.put(UUID+"#22", card.getOwner().getClientPseudo());
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
        FF8CardName.put(UUID+"#32", card.getName());
        FF8CardUp.put(UUID+"#32", card.getUp());
        FF8CardRight.put(UUID+"#32", card.getRight());
        FF8CardDown.put(UUID+"#32", card.getDown());
        FF8CardLeft.put(UUID+"#32", card.getLeft());
        FF8CardOwner.put(UUID+"#32", card.getOwner().getClientPseudo());
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
        FF8CardName.put(UUID+"#13", card.getName());
        FF8CardUp.put(UUID+"#13", card.getUp());
        FF8CardRight.put(UUID+"#13", card.getRight());
        FF8CardDown.put(UUID+"#13", card.getDown());
        FF8CardLeft.put(UUID+"#13", card.getLeft());
        FF8CardOwner.put(UUID+"#13", card.getOwner().getClientPseudo());
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
        FF8CardName.put(UUID+"#23", card.getName());
        FF8CardUp.put(UUID+"#23", card.getUp());
        FF8CardRight.put(UUID+"#23", card.getRight());
        FF8CardDown.put(UUID+"#23", card.getDown());
        FF8CardLeft.put(UUID+"#23", card.getLeft());
        FF8CardOwner.put(UUID+"#23", card.getOwner().getClientPseudo());
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
        FF8CardName.put(UUID+"#33", card.getName());
        FF8CardUp.put(UUID+"#33", card.getUp());
        FF8CardRight.put(UUID+"#33", card.getRight());
        FF8CardDown.put(UUID+"#33", card.getDown());
        FF8CardLeft.put(UUID+"#33", card.getLeft());
        FF8CardOwner.put(UUID+"#33", card.getOwner().getClientPseudo());
        if(FF8GameClientList.containsKey(UUID)){
            FF8GameClientList.get(UUID).add(client);
        }else{
            FF8GameClientList.put(UUID,new ArrayList<>(Arrays.asList(client)));
        }
        for(IClient clt : FF8GameClientList.get(UUID)){
            clt.refreshDisplayCard();
        }
    }

    public Map<String,String> getFF8Card() throws RemoteException{
        return this.FF8CardName;
    }

    public void initFF8GameClientList(String UUID, IClient client) throws RemoteException{
        if(!this.FF8GameClientList.containsKey(UUID)){
            this.FF8GameClientList.put(UUID, new ArrayList<>(Arrays.asList(client)));
        }else{
            this.FF8GameClientList.get(UUID).add(1,client);
        }
    }

    public List<IClient> getFF8GameClientList(String UUID) throws RemoteException{
        return this.FF8GameClientList.get(UUID);
    }

    public Map<String,Integer> getFF8CardUp() throws RemoteException{
        return this.FF8CardUp;
    }
    public Map<String,Integer> getFF8CardRight() throws RemoteException{
        return this.FF8CardRight;
    }
    public Map<String,Integer> getFF8CardDown() throws RemoteException{
        return this.FF8CardDown;
    }
    public Map<String,Integer> getFF8CardLeft() throws RemoteException{
        return this.FF8CardLeft;
    }
    public Map<String,String> getFF8CardOwner() throws RemoteException{
        return this.FF8CardOwner;
    }

    @Override
    public boolean setFF8CardOwner(String UUID, String position, String newOwner) throws RemoteException {
        this.FF8CardOwner.replace(UUID+"#"+position, newOwner);
        if (this.FF8CardOwner.get(UUID+"#"+position).equals(newOwner)){
            System.out.println("return true");
            return true;
        }
        System.out.println("return false");
        return false;
    }

}
