package fr.playcards;

import fr.playcards.cardgame.FF8TripleTriade;
import fr.playcards.room.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class PlayCardsController {

    @FXML
    protected void onButtonClick() {
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