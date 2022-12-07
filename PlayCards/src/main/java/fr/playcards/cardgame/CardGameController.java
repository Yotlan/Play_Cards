package fr.playcards.cardgame;

//Import part
import fr.playcards.client.IClient;

/*
@author Yotlan LE CROM
@author Endy YU

This interface have for goal to control this card game frame.
 */

public interface CardGameController {
    public void setGame(CardGame game, IClient client);
    public void refresh();

}
