package fr.playcards.room;

import fr.playcards.cardgame.*;

import java.rmi.Naming;

public class CRoom {

    public static void main(String[] argv) {
        try {
            IRoom room =
                    (IRoom) Naming.lookup(
                            "play-cards/1099/createroom");
            new SRoom(new FF8TripleTriade());
        } catch (Exception e) {
            System.out.println("erreur" + e);
        }
    }

}
