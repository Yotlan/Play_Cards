package fr.playcards.server;

//Import part

import fr.playcards.cardgame.CardGame;
import fr.playcards.cardgame.card.Card;
import fr.playcards.client.IClient;
import fr.playcards.room.IRoom;
import fr.playcards.room.Room;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends UnicastRemoteObject implements IServer {

    public List<IRoom> observableRoomList = new ArrayList<>();
    public List<IClient> clientList = new ArrayList<>();
    public String name;
    public Map<String,Card> FF8Card = new HashMap<>();
    public Map<String,String> FF8CardName = new HashMap<>();
    public Map<String,Integer> FF8CardLevel = new HashMap<>();
    public Map<String,Integer> FF8CardUp = new HashMap<>();
    public Map<String,Integer> FF8CardRight = new HashMap<>();
    public Map<String,Integer> FF8CardDown = new HashMap<>();
    public Map<String,Integer> FF8CardLeft = new HashMap<>();
    public Map<String,String> FF8CardOwner = new HashMap<>();
    public Map<String,List<IClient>> FF8GameClientList = new HashMap<>();
    public Map<String,Integer> FF8GameClientTurn = new HashMap<>();

    public boolean isCreate;
    public boolean isInit;
    public boolean isSwitch;
    public boolean isDisplay11;
    public boolean isDisplay12;
    public boolean isDisplay13;
    public boolean isDisplay21;
    public boolean isDisplay22;
    public boolean isDisplay23;
    public boolean isDisplay31;
    public boolean isDisplay32;
    public boolean isDisplay33;
    public boolean isInitFF8Client;
    public boolean isSettingFF8CardOwner;

    public Server(String name) throws RemoteException{
        this.name = name;
        this.isCreate = false;
        this.isInit = false;
        this.isSwitch = false;
        this.isDisplay11 = false;
        this.isDisplay12 = false;
        this.isDisplay13 = false;
        this.isDisplay21 = false;
        this.isDisplay22 = false;
        this.isDisplay23 = false;
        this.isDisplay31 = false;
        this.isDisplay32 = false;
        this.isDisplay33 = false;
        this.isInitFF8Client = false;
        this.isSettingFF8CardOwner = false;
    }

    public synchronized void createRoom(CardGame game,IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread create a room, and so if another thread create a room, we wait. The goal of
        this is to avoid the overload of the Server.
         */
        while(isCreate){
            wait();
        }
        this.isCreate = true;
        //Critical Section
        observableRoomList.add(new Room(game));
        clientList.add(client);
        for(IClient clt : clientList){
            clt.refreshRoom();
        }
        this.isCreate = false;
        notifyAll();
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

    public synchronized void initTurn(String UUID) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread want to init turn for another game, and so if another thread init turn for
        another game, we waiting to him finish. The goal of this is to avoid overloading of the server.
         */
        while(isInit){
            wait();
        }
        this.isInit = true;
        //Critical Section
        if(!FF8GameClientTurn.containsKey(UUID)) {
            FF8GameClientTurn.put(UUID,1);
        }
        this.isInit = false;
        notifyAll();
    }

    public synchronized void switchTurn(String UUID) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread want to switch turn for another game, and so if another thread switch turn for
        another game, we waiting to him finish. The goal of this is to avoid overloading of the server.
         */
        while(isSwitch){
            wait();
        }
        this.isSwitch = true;
        //Critical Section
        if(FF8GameClientTurn.containsKey(UUID)){
            if(FF8GameClientTurn.get(UUID) == 1){
                FF8GameClientTurn.replace(UUID,2);
            }else{
                FF8GameClientTurn.replace(UUID,1);
            }
        }
        this.isSwitch = false;
        notifyAll();
    }

    public Integer getTurn(String UUID) throws RemoteException {
        return this.FF8GameClientTurn.get(UUID);
    }

    public synchronized void displayCard11(Card card, String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread display card, and so we waiting to him finish. The goal of this is to avoid
        overloading of the server.
         */
        while(isDisplay11){
            wait();
        }
        this.isDisplay11 = true;
        //Critical Section
        FF8Card.put(UUID+"#11",card);
        FF8CardName.put(UUID+"#11", card.getName());
        FF8CardLevel.put(UUID+"#11", card.getLevel());
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
        this.isDisplay11 = false;
        notifyAll();
    }
    public synchronized void displayCard21(Card card, String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread display card, and so we waiting to him finish. The goal of this is to avoid
        overloading of the server.
         */
        while(isDisplay21){
            wait();
        }
        this.isDisplay21 = true;
        //Critical Section
        FF8Card.put(UUID+"#21",card);
        FF8CardName.put(UUID+"#21", card.getName());
        FF8CardLevel.put(UUID+"#21", card.getLevel());
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
        this.isDisplay21 = false;
        notifyAll();
    }
    public synchronized void displayCard31(Card card, String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread display card, and so we waiting to him finish. The goal of this is to avoid
        overloading of the server.
         */
        while(isDisplay31){
            wait();
        }
        this.isDisplay31 = true;
        //Critical Section
        FF8Card.put(UUID+"#31",card);
        FF8CardName.put(UUID+"#31", card.getName());
        FF8CardLevel.put(UUID+"#31", card.getLevel());
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
        this.isDisplay31 = false;
        notifyAll();
    }
    public synchronized void displayCard12(Card card, String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread display card, and so we waiting to him finish. The goal of this is to avoid
        overloading of the server.
         */
        while(isDisplay12){
            wait();
        }
        this.isDisplay12 = true;
        //Critical Section
        FF8Card.put(UUID+"#12",card);
        FF8CardName.put(UUID+"#12", card.getName());
        FF8CardLevel.put(UUID+"#12", card.getLevel());
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
        this.isDisplay12 = false;
        notifyAll();
    }
    public synchronized void displayCard22(Card card, String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread display card, and so we waiting to him finish. The goal of this is to avoid
        overloading of the server.
         */
        while(isDisplay22){
            wait();
        }
        this.isDisplay22 = true;
        //Critical Section
        FF8Card.put(UUID+"#22",card);
        FF8CardName.put(UUID+"#22", card.getName());
        FF8CardLevel.put(UUID+"#22", card.getLevel());
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
        this.isDisplay22 = false;
        notifyAll();
    }
    public synchronized void displayCard32(Card card, String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread display card, and so we waiting to him finish. The goal of this is to avoid
        overloading of the server.
         */
        while(isDisplay32){
            wait();
        }
        this.isDisplay32 = true;
        //Critical Section
        FF8Card.put(UUID+"#32",card);
        FF8CardName.put(UUID+"#32", card.getName());
        FF8CardLevel.put(UUID+"#32", card.getLevel());
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
        this.isDisplay32 = false;
        notifyAll();
    }
    public synchronized void displayCard13(Card card, String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread display card, and so we waiting to him finish. The goal of this is to avoid
        overloading of the server.
         */
        while(isDisplay13){
            wait();
        }
        this.isDisplay13 = true;
        //Critical Section
        FF8Card.put(UUID+"#13",card);
        FF8CardName.put(UUID+"#13", card.getName());
        FF8CardLevel.put(UUID+"#13", card.getLevel());
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
        this.isDisplay13 = false;
        notifyAll();
    }
    public synchronized void displayCard23(Card card, String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread display card, and so we waiting to him finish. The goal of this is to avoid
        overloading of the server.
         */
        while(isDisplay23){
            wait();
        }
        this.isDisplay23 = true;
        //Critical Section
        FF8Card.put(UUID+"#23",card);
        FF8CardName.put(UUID+"#23", card.getName());
        FF8CardLevel.put(UUID+"#23", card.getLevel());
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
        this.isDisplay23 = false;
        notifyAll();
    }
    public synchronized void displayCard33(Card card, String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread display card, and so we waiting to him finish. The goal of this is to avoid
        overloading of the server.
         */
        while(isDisplay33){
            wait();
        }
        this.isDisplay33 = true;
        //Critical Section
        FF8Card.put(UUID+"#33",card);
        FF8CardName.put(UUID+"#33", card.getName());
        FF8CardLevel.put(UUID+"#33", card.getLevel());
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
        this.isDisplay33 = false;
        notifyAll();
    }

    public synchronized void initFF8GameClientList(String UUID, IClient client) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread init FF8 Client, and so if another thread init FF8 Client, we waiting for him
        to finish. The goal of this is to avoid overloading of the Server.
         */
        while(isInitFF8Client){
            wait();
        }
        this.isInitFF8Client = true;
        //Critical Section
        if(!this.FF8GameClientList.containsKey(UUID)){
            this.FF8GameClientList.put(UUID, new ArrayList<>(Arrays.asList(client)));
        }else{
            this.FF8GameClientList.get(UUID).add(1,client);
        }
        this.isInitFF8Client = false;
        notifyAll();
    }

    public List<IClient> getFF8GameClientList(String UUID) throws RemoteException{
        return this.FF8GameClientList.get(UUID);
    }

    public Map<String,String> getFF8CardName() throws RemoteException{
        return this.FF8CardName;
    }

    public Map<String,Integer> getFF8CardLevel() throws RemoteException{
        return this.FF8CardLevel;
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

    public synchronized boolean setFF8CardOwner(String UUID, String position, String newOwner) throws RemoteException, InterruptedException {
        /*
        We need to check if another thread set FF8 Card Owner, and so if another thread set FF8 Card Owner, we waiting
        for him to finish. The goal of this is to avoid overloading of the Server.
         */
        while(isSettingFF8CardOwner){
            wait();
        }
        this.isSettingFF8CardOwner = true;
        //Critical Section
        this.FF8CardOwner.replace(UUID+"#"+position, newOwner);
        if (this.FF8CardOwner.get(UUID+"#"+position).equals(newOwner)){
            for ( IClient client : this.getFF8GameClientList(UUID)){
                client.refreshDisplayCard();
            }
            this.isSettingFF8CardOwner = false;
            notifyAll();
            return true;
        }
        this.isSettingFF8CardOwner = false;
        notifyAll();
        return false;
    }

}
