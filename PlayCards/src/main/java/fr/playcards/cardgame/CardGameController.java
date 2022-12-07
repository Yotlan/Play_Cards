package fr.playcards.cardgame;

//Import part
import fr.playcards.client.IClient;

/*
@author Yotlan LE CROM
@author Endy YU

This interface have for goal to control this card game frame.
 */

public interface CardGameController {

    /*
    @param CardGame game
    @param IClient client

    This method link a controller to the server which are the client client.
     */

    void setGame(CardGame game, IClient client);

    /*
    This method refresh the controller information by asking the server for the update.
     */

    void refresh();

}
