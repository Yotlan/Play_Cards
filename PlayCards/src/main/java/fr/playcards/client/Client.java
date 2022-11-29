package fr.playcards.client;

import fr.playcards.cardgame.card.Card;
import fr.playcards.server.IServer;
import fr.playcards.room.IRoom;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client implements IClient {

    public String clientPseudoName;
    public List<IRoom> observableRoomList = new ArrayList<>();
    public IServer mainServer;

    public Map<String,Card> FF8Card = new HashMap<>();

    public Client(String clientName) {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            mainServer = (IServer) registry.lookup("play-cards/1099/connecting");
            this.observableRoomList = mainServer.getObservableRoomList();
            this.clientPseudoName=clientName;
        } catch (Exception e) {
            System.out.println("Client Constructor Error : "+e);
        }
    }

    public void refreshRoom() {
        try {
            this.observableRoomList = this.mainServer.getObservableRoomList();
        } catch (Exception e) {
            System.out.println("Client refresh method Error : "+e);
        }
    }

    public List<IRoom> getObservableRoomList() {
        refreshRoom();
        return this.observableRoomList;
    }

    public IServer getMainServer() {
        return this.mainServer;
    }

    public void refreshDisplayCard() {
        try{
            this.FF8Card = this.mainServer.getFF8Card();
        } catch (Exception e) {
            System.out.println("Client refresh method Error : "+e);
        }
    }

    public String getClientPseudo(){
        return this.clientPseudoName;
    }
    public Map<String, Card> getFF8Card(String UUID) {
        refreshDisplayCard();
        Map<String, Card> returnedMap = new HashMap<>();
        returnedMap.put("11",this.FF8Card.get(UUID+"#11"));
        returnedMap.put("21",this.FF8Card.get(UUID+"#21"));
        returnedMap.put("31",this.FF8Card.get(UUID+"#31"));
        returnedMap.put("12",this.FF8Card.get(UUID+"#12"));
        returnedMap.put("22",this.FF8Card.get(UUID+"#22"));
        returnedMap.put("32",this.FF8Card.get(UUID+"#32"));
        returnedMap.put("13",this.FF8Card.get(UUID+"#13"));
        returnedMap.put("23",this.FF8Card.get(UUID+"#23"));
        returnedMap.put("33",this.FF8Card.get(UUID+"#33"));
        return returnedMap;
    }

}
