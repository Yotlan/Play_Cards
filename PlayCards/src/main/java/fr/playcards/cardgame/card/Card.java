package fr.playcards.cardgame.card;

//Import part
import fr.playcards.client.Client;
import java.io.Serializable;

/*
@author Yotlan LE CROM
@author Endy YU

This interface have for goal to extends Serializable interface and so to viewing this object in shared frame.
 */

public interface Card extends Serializable {

    //Getter/Setter card's information method
    String getName();
    int getUp();
    int getRight();
    int getDown();
    int getLeft();
    String getElement();
    Client getOwner();
    void setOwner(Client newOwner);
}
