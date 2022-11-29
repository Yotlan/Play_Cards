package fr.playcards.cardgame;

import fr.playcards.cardgame.card.Card;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class KoiKoiWars extends UnicastRemoteObject implements CardGame {

    private Integer MAX_PLAYER;
    public String UUID;
    public CardGameController controller;
    public List<Card> Player1_Card = new ArrayList<>();
    public List<Card> Player2_Card = new ArrayList<>();
    public String Player1_Pseudo = "";
    public String Player2_Pseudo = "";

    public KoiKoiWars(String UUID) throws IOException {
        MAX_PLAYER = 2;
        this.UUID = UUID;
    }
    @Override
    public String getName() {
        return "Koi Koi Wars - Sakura Wars";
    }

    public Integer getMaxPlayer() {
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

    @Override
    public void removePlayer1Card(Card card) throws RemoteException {
        this.Player1_Card.remove(card);
    }

    public List<Card> getPlayer2Card() throws RemoteException{
        return this.Player2_Card;
    }

    @Override
    public void removePlayer2Card(Card card) throws RemoteException {
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
