package fr.playcards.cardgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.playcards.cardgame.card.Card;
import fr.playcards.cardgame.card.FF8Card;

import java.io.IOException;
import java.nio.file.Paths;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FF8TripleTriade extends UnicastRemoteObject implements CardGame{

    ObjectMapper JSON_MAPPER = new ObjectMapper();
    Map<?, ?> JSON_MAP;
    List<FF8Card> LVL1 = new ArrayList<FF8Card>();
    private Integer MAX_PLAYER;
    public String UUID;
    public CardGameController controller;
    public List<Card> Player1_Card = new ArrayList<Card>();
    public String Player1_Pseudo = "";
    public List<Card> Player2_Card = new ArrayList<Card>();
    public String Player2_Pseudo = "";

    public FF8TripleTriade(String UUID) throws IOException {
        MAX_PLAYER = 2;
        JSON_MAP = JSON_MAPPER.readValue(Paths.get("../Triple_Triade/FF8/json/lvl1.json").toFile(), Map.class);
        for(Object name : JSON_MAP.keySet()){
            LVL1.add(
                    new FF8Card(
                            (String) name,
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("up"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("right"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("down"),
                            (Integer) ((Map<?,?>) JSON_MAP.get(name)).get("left"),
                            (String) ((Map<?,?>) JSON_MAP.get(name)).get("element")
                    )
            );
        }
        for(int i=0; i<10; i++){
            int index = new Random().nextInt(LVL1.size());
            if(i<5){
                Player1_Card.add(LVL1.get(index));
            }else{
                Player2_Card.add(LVL1.get(index));
            }
        }
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
    public void removePlayer1_oneCard(Card card) throws RemoteException{
        this.Player1_Card.remove(card);
    }


    public List<Card> getPlayer2Card() throws RemoteException {
        return this.Player2_Card;
    }

    public void getPlayer2_oneCard(Card card) throws RemoteException{
        this.Player2_Card.remove(card);
    }

    public void removePlayer1Card(Card card) throws RemoteException{
        this.Player1_Card.remove(card);
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
