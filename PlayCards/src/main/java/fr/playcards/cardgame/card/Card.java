package fr.playcards.cardgame.card;

import fr.playcards.client.Client;

import java.io.Serializable;

public interface Card extends Serializable {
    public String getName();

    public int getUp();

    public int getRight();

    public int getDown();

    public int getLeft();

    public String getElement();

    public Client getOwner();

    public void setOwner(Client newOwner);
}
