package fr.playcards.cardgame.card;

import java.io.Serializable;

public interface Card extends Serializable {
    public String getName();

    public int getUp();

    public int getRight();

    public int getDown();

    public int getLeft();

    public String getElement();
}
