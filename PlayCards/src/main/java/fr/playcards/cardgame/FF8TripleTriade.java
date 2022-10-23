package fr.playcards.cardgame;

public class FF8TripleTriade implements CardGame {

    private Integer MAX_PLAYER;

    public FF8TripleTriade() {
        MAX_PLAYER = 2;
    }

    @Override
    public String toString() {
        return "Triple Triade - Final Fantasy 8";
    }

    public Integer getMaxPlayer() {
        return MAX_PLAYER;
    }

}
