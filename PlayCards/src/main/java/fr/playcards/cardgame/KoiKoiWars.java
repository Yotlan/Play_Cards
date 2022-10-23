package fr.playcards.cardgame;

public class KoiKoiWars implements CardGame {

    private Integer MAX_PLAYER;

    public KoiKoiWars() {
        MAX_PLAYER = 2;
    }
    @Override
    public String toString() {
        return "Koi Koi Wars - Sakura Wars";
    }

    public Integer getMaxPlayer() {
        return MAX_PLAYER;
    }

}
