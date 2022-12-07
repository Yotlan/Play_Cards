package fr.playcards.cardgame;

//Import part
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.playcards.cardgame.card.Card;
import fr.playcards.cardgame.card.FF8Card;
import java.io.IOException;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FF8TripleTriade extends UnicastRemoteObject implements CardGame{
    ObjectMapper JSON_MAPPER = new ObjectMapper();
    Map<?, ?> JSON_MAP;
    List<FF8Card> LVL1 = new ArrayList<>();
    List<FF8Card> LVL2 = new ArrayList<>();
    List<FF8Card> LVL3 = new ArrayList<>();
    List<FF8Card> LVL4 = new ArrayList<>();
    List<FF8Card> LVL5 = new ArrayList<>();
    List<FF8Card> LVL6 = new ArrayList<>();
    List<FF8Card> LVL7 = new ArrayList<>();
    List<FF8Card> LVL8 = new ArrayList<>();
    List<FF8Card> LVL9 = new ArrayList<>();
    List<FF8Card> LVL10 = new ArrayList<>();
    private Integer MAX_PLAYER;
    public String UUID;
    public CardGameController controller;
    public List<Card> Player1_Card = new ArrayList<Card>();
    public String Player1_Pseudo = "N/A";
    public List<Card> Player2_Card = new ArrayList<Card>();
    public String Player2_Pseudo = "N/A";

    public Card EmptyCard = new FF8Card("Empty",0,0,0,0,0,"empty");

    public FF8TripleTriade(String UUID) throws IOException {
        MAX_PLAYER = 2;
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl1.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL1.add(
                    new FF8Card(
                            (String) name,
                            1,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl2.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL2.add(
                    new FF8Card(
                            (String) name,
                            2,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl3.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL3.add(
                    new FF8Card(
                            (String) name,
                            3,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl4.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL4.add(
                    new FF8Card(
                            (String) name,
                            4,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl5.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL5.add(
                    new FF8Card(
                            (String) name,
                            5,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl6.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL6.add(
                    new FF8Card(
                            (String) name,
                            6,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl7.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL7.add(
                    new FF8Card(
                            (String) name,
                            7,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl8.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL8.add(
                    new FF8Card(
                            (String) name,
                            8,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl9.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL9.add(
                    new FF8Card(
                            (String) name,
                            9,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl10.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL10.add(
                    new FF8Card(
                            (String) name,
                            10,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        //Select the lvl card in the pool 1
        int lvlPool1 = new Random().nextInt(1,6);
        if(lvlPool1==1){
            Player1_Card.add(LVL1.remove(new Random().nextInt(LVL1.size())));
        }else if(lvlPool1==2){
            Player1_Card.add(LVL2.remove(new Random().nextInt(LVL2.size())));
        }else if(lvlPool1==3){
            Player1_Card.add(LVL3.remove(new Random().nextInt(LVL3.size())));
        }else if(lvlPool1==4){
            Player1_Card.add(LVL4.remove(new Random().nextInt(LVL4.size())));
        }else if(lvlPool1==5){
            Player1_Card.add(LVL5.remove(new Random().nextInt(LVL5.size())));
        }

        //Select lvls cards in the pool 2
        int lvl1Pool2 = new Random().nextInt(6,8);
        int lvl2Pool2 = new Random().nextInt(6,8);
        if(lvl1Pool2==6){
            Player1_Card.add(LVL6.remove(new Random().nextInt(LVL6.size())));
        }else if(lvl1Pool2==7){
            Player1_Card.add(LVL6.remove(new Random().nextInt(LVL7.size())));
        }
        if(lvl2Pool2==6){
            Player1_Card.add(LVL6.remove(new Random().nextInt(LVL6.size())));
        }else if(lvl2Pool2==7){
            Player1_Card.add(LVL7.remove(new Random().nextInt(LVL7.size())));
        }

        //Select lvl card in the pool 3
        int lvlPool3 = new Random().nextInt(8,10);
        if(lvlPool3==8){
            Player1_Card.add(LVL8.remove(new Random().nextInt(LVL8.size())));
        }else if(lvlPool3==9){
            Player1_Card.add(LVL9.remove(new Random().nextInt(LVL9.size())));
        }

        //Select lvl card in the pool 4
        Player1_Card.add(LVL10.remove(new Random().nextInt(LVL10.size())));

        //Select the lvl card in the pool 1
        lvlPool1 = new Random().nextInt(1,6);
        if(lvlPool1==1){
            Player2_Card.add(LVL1.remove(new Random().nextInt(LVL1.size())));
        }else if(lvlPool1==2){
            Player2_Card.add(LVL2.remove(new Random().nextInt(LVL2.size())));
        }else if(lvlPool1==3){
            Player2_Card.add(LVL3.remove(new Random().nextInt(LVL3.size())));
        }else if(lvlPool1==4){
            Player2_Card.add(LVL4.remove(new Random().nextInt(LVL4.size())));
        }else if(lvlPool1==5){
            Player2_Card.add(LVL5.remove(new Random().nextInt(LVL5.size())));
        }

        //Select lvls cards in the pool 2
        lvl1Pool2 = new Random().nextInt(6,8);
        lvl2Pool2 = new Random().nextInt(6,8);
        if(lvl1Pool2==6){
            Player2_Card.add(LVL6.remove(new Random().nextInt(LVL6.size())));
        }else if(lvl1Pool2==7){
            Player2_Card.add(LVL6.remove(new Random().nextInt(LVL7.size())));
        }
        if(lvl2Pool2==6){
            Player2_Card.add(LVL6.remove(new Random().nextInt(LVL6.size())));
        }else if(lvl2Pool2==7){
            Player2_Card.add(LVL7.remove(new Random().nextInt(LVL7.size())));
        }

        //Select lvl card in the pool 3
        lvlPool3 = new Random().nextInt(8,10);
        if(lvlPool3==8){
            Player2_Card.add(LVL8.remove(new Random().nextInt(LVL8.size())));
        }else if(lvlPool3==9){
            Player2_Card.add(LVL9.remove(new Random().nextInt(LVL9.size())));
        }

        //Select lvl card in the pool 4
        Player2_Card.add(LVL10.remove(new Random().nextInt(LVL10.size())));
        this.UUID = UUID;
    }

    public String getName() throws RemoteException{
        return "Triple Triade - Final Fantasy 8";
    }
    public Integer getMaxPlayer() throws RemoteException{
        return MAX_PLAYER;
    }
    public String getUUID() throws RemoteException{
        return this.UUID;
    }
    public CardGameController getController() throws RemoteException{
        return this.controller;
    }
    public void setController(CardGameController controller) throws RemoteException{
        this.controller = controller;
    }
    public List<Card> getPlayer1Card() throws RemoteException{
        return this.Player1_Card;
    }
    public void removePlayer1Card(int index) throws RemoteException{
        Player1_Card.set(index,EmptyCard);
    }
    public List<Card> getPlayer2Card() throws RemoteException {
        return this.Player2_Card;
    }
    public void removePlayer2Card(int index) throws RemoteException {
        Player2_Card.set(index,EmptyCard);
    }
    public String getPlayer1Pseudo() throws RemoteException{
        return Player1_Pseudo;
    }
    public void setPlayer1Pseudo(String pseudo) throws RemoteException{
        this.Player1_Pseudo = pseudo;
    }
    public String getPlayer2Pseudo() throws RemoteException{
        return this.Player2_Pseudo;
    }
    public void setPlayer2Pseudo(String pseudo) throws RemoteException{
        this.Player2_Pseudo = pseudo;
    }

}
