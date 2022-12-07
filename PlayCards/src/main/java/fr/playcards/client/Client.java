package fr.playcards.client;

//Import part
import fr.playcards.server.IServer;
import fr.playcards.room.IRoom;
import java.rmi.RemoteException;
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
    public Map<String,String> FF8CardName = new HashMap<>();
    public Map<String,Integer> FF8CardLevel = new HashMap<>();
    public Map<String,Integer> FF8CardUp = new HashMap<>();
    public Map<String,Integer> FF8CardRight = new HashMap<>();
    public Map<String,Integer> FF8CardDown = new HashMap<>();
    public Map<String,Integer> FF8CardLeft = new HashMap<>();
    public Map<String,String> FF8CardOwner = new HashMap<>();

    public Client() {
        try {
            //Connect client to the server
            Registry registry = LocateRegistry.getRegistry(1099);
            mainServer = (IServer) registry.lookup("play-cards/1099/connecting");
            this.observableRoomList = mainServer.getObservableRoomList();
            this.clientPseudoName="N/A";
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
            this.FF8CardName = this.mainServer.getFF8CardName();
            this.FF8CardLevel = this.mainServer.getFF8CardLevel();
            this.FF8CardUp = this.mainServer.getFF8CardUp();
            this.FF8CardRight = this.mainServer.getFF8CardRight();
            this.FF8CardDown = this.mainServer.getFF8CardDown();
            this.FF8CardLeft = this.mainServer.getFF8CardLeft();
            this.FF8CardOwner = this.mainServer.getFF8CardOwner();
        } catch (Exception e) {
            System.out.println("Client refresh method Error : "+e);
        }
    }

    public String getClientPseudo(){
        return this.clientPseudoName;
    }
    public void setClientPseudo(String pseudo) {
        this.clientPseudoName=pseudo;
    }

    public Map<String, String> getFF8CardName(String UUID) {
        refreshDisplayCard();
        Map<String, String> returnedMap = new HashMap<>();
        returnedMap.put("11",this.FF8CardName.get(UUID+"#11"));
        returnedMap.put("21",this.FF8CardName.get(UUID+"#21"));
        returnedMap.put("31",this.FF8CardName.get(UUID+"#31"));
        returnedMap.put("12",this.FF8CardName.get(UUID+"#12"));
        returnedMap.put("22",this.FF8CardName.get(UUID+"#22"));
        returnedMap.put("32",this.FF8CardName.get(UUID+"#32"));
        returnedMap.put("13",this.FF8CardName.get(UUID+"#13"));
        returnedMap.put("23",this.FF8CardName.get(UUID+"#23"));
        returnedMap.put("33",this.FF8CardName.get(UUID+"#33"));
        return returnedMap;
    }
    public Map<String,Integer> getFF8CardLevel(String UUID) throws RemoteException{
        refreshDisplayCard();
        Map<String, Integer> returnedMap = new HashMap<>();
        returnedMap.put("11",this.FF8CardLevel.get(UUID+"#11"));
        returnedMap.put("21",this.FF8CardLevel.get(UUID+"#21"));
        returnedMap.put("31",this.FF8CardLevel.get(UUID+"#31"));
        returnedMap.put("12",this.FF8CardLevel.get(UUID+"#12"));
        returnedMap.put("22",this.FF8CardLevel.get(UUID+"#22"));
        returnedMap.put("32",this.FF8CardLevel.get(UUID+"#32"));
        returnedMap.put("13",this.FF8CardLevel.get(UUID+"#13"));
        returnedMap.put("23",this.FF8CardLevel.get(UUID+"#23"));
        returnedMap.put("33",this.FF8CardLevel.get(UUID+"#33"));
        return returnedMap;
    }
    public Map<String,Integer> getFF8CardUp(String UUID) throws RemoteException{
        refreshDisplayCard();
        Map<String, Integer> returnedMap = new HashMap<>();
        returnedMap.put("11",this.FF8CardUp.get(UUID+"#11"));
        returnedMap.put("21",this.FF8CardUp.get(UUID+"#21"));
        returnedMap.put("31",this.FF8CardUp.get(UUID+"#31"));
        returnedMap.put("12",this.FF8CardUp.get(UUID+"#12"));
        returnedMap.put("22",this.FF8CardUp.get(UUID+"#22"));
        returnedMap.put("32",this.FF8CardUp.get(UUID+"#32"));
        returnedMap.put("13",this.FF8CardUp.get(UUID+"#13"));
        returnedMap.put("23",this.FF8CardUp.get(UUID+"#23"));
        returnedMap.put("33",this.FF8CardUp.get(UUID+"#33"));
        return returnedMap;
    }
    public Map<String,Integer> getFF8CardRight(String UUID) throws RemoteException{
        refreshDisplayCard();
        Map<String, Integer> returnedMap = new HashMap<>();
        returnedMap.put("11",this.FF8CardRight.get(UUID+"#11"));
        returnedMap.put("21",this.FF8CardRight.get(UUID+"#21"));
        returnedMap.put("31",this.FF8CardRight.get(UUID+"#31"));
        returnedMap.put("12",this.FF8CardRight.get(UUID+"#12"));
        returnedMap.put("22",this.FF8CardRight.get(UUID+"#22"));
        returnedMap.put("32",this.FF8CardRight.get(UUID+"#32"));
        returnedMap.put("13",this.FF8CardRight.get(UUID+"#13"));
        returnedMap.put("23",this.FF8CardRight.get(UUID+"#23"));
        returnedMap.put("33",this.FF8CardRight.get(UUID+"#33"));
        return returnedMap;
    }
    public Map<String,Integer> getFF8CardDown(String UUID) throws RemoteException{
        refreshDisplayCard();
        Map<String, Integer> returnedMap = new HashMap<>();
        returnedMap.put("11",this.FF8CardDown.get(UUID+"#11"));
        returnedMap.put("21",this.FF8CardDown.get(UUID+"#21"));
        returnedMap.put("31",this.FF8CardDown.get(UUID+"#31"));
        returnedMap.put("12",this.FF8CardDown.get(UUID+"#12"));
        returnedMap.put("22",this.FF8CardDown.get(UUID+"#22"));
        returnedMap.put("32",this.FF8CardDown.get(UUID+"#32"));
        returnedMap.put("13",this.FF8CardDown.get(UUID+"#13"));
        returnedMap.put("23",this.FF8CardDown.get(UUID+"#23"));
        returnedMap.put("33",this.FF8CardDown.get(UUID+"#33"));
        return returnedMap;
    }
    public Map<String,Integer> getFF8CardLeft(String UUID) throws RemoteException{
        refreshDisplayCard();
        Map<String, Integer> returnedMap = new HashMap<>();
        returnedMap.put("11",this.FF8CardLeft.get(UUID+"#11"));
        returnedMap.put("21",this.FF8CardLeft.get(UUID+"#21"));
        returnedMap.put("31",this.FF8CardLeft.get(UUID+"#31"));
        returnedMap.put("12",this.FF8CardLeft.get(UUID+"#12"));
        returnedMap.put("22",this.FF8CardLeft.get(UUID+"#22"));
        returnedMap.put("32",this.FF8CardLeft.get(UUID+"#32"));
        returnedMap.put("13",this.FF8CardLeft.get(UUID+"#13"));
        returnedMap.put("23",this.FF8CardLeft.get(UUID+"#23"));
        returnedMap.put("33",this.FF8CardLeft.get(UUID+"#33"));
        return returnedMap;
    }
    public Map<String,String> getFF8CardOwner(String UUID) throws RemoteException{
        refreshDisplayCard();
        Map<String, String> returnedMap = new HashMap<>();
        returnedMap.put("11",this.FF8CardOwner.get(UUID+"#11"));
        returnedMap.put("21",this.FF8CardOwner.get(UUID+"#21"));
        returnedMap.put("31",this.FF8CardOwner.get(UUID+"#31"));
        returnedMap.put("12",this.FF8CardOwner.get(UUID+"#12"));
        returnedMap.put("22",this.FF8CardOwner.get(UUID+"#22"));
        returnedMap.put("32",this.FF8CardOwner.get(UUID+"#32"));
        returnedMap.put("13",this.FF8CardOwner.get(UUID+"#13"));
        returnedMap.put("23",this.FF8CardOwner.get(UUID+"#23"));
        returnedMap.put("33",this.FF8CardOwner.get(UUID+"#33"));
        return returnedMap;
    }

}
