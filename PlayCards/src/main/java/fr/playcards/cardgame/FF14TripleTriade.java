package fr.playcards.cardgame;

public class FF14TripleTriade implements CardGame {

    private Integer MAX_PLAYER;

    public FF14TripleTriade() {
        MAX_PLAYER = 2;
    }
    @Override
    public String toString() {
        return "Triple Triade - Final Fantasy 14";
    }

    public Integer getMaxPlayer() {
        return MAX_PLAYER;
    }

}
