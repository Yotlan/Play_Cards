package fr.playcards.cardgame;

import java.io.Serializable;

public interface CardGame extends Serializable {

    public String toString();

    public Integer getMaxPlayer();

}
