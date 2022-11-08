package fr.playcards.cardgame;

import fr.playcards.client.IClient;

import java.io.Serializable;

public interface CardGameController {

    public void setGame(CardGame game, IClient client);

    public void refresh();

}
