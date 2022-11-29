package fr.playcards.client;

import fr.playcards.cardgame.card.Card;
import fr.playcards.room.IRoom;
import fr.playcards.server.IServer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IClient extends Serializable {

    public void refreshRoom();
    public List<IRoom> getObservableRoomList();

    public IServer getMainServer();

    public void refreshDisplayCard();

    public String getClientPseudo();

    public void setClientPseudo(String pseudo);

    public Map<String, Card> getFF8Card(String UUID);

}
