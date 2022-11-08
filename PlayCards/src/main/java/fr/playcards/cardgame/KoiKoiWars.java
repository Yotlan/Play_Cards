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

    public List<Card> getPlayer2Card() throws RemoteException{
        return this.Player2_Card;
    }
}
