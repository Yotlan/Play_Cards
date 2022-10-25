package fr.playcards;

import fr.playcards.cardgame.*;
import fr.playcards.room.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class StartServer {

    @FXML
    public static List<IRoom> observableRoomList = new ArrayList<>();
    public static IRMIObservableList<IRoom> backup;
    public static void main(String[] argv) {
        try {
            LocateRegistry.createRegistry(1099);
            while(true) {
                backup = new RMIObservableList<IRoom>(observableRoomList);
                Naming.rebind(
                        "play-cards/1099/observablelist",
                        backup);
                Naming.rebind(
                        "play-cards/1099/createroomff8tt",
                        new SRoom(new FF8TripleTriade()));
                Naming.rebind(
                        "play-cards/1099/createroomff14tt",
                        new SRoom(new FF14TripleTriade()));
                Naming.rebind(
                        "play-cards/1099/createroomkkw",
                        new SRoom(new KoiKoiWars()));
                try {
                    backup = (IRMIObservableList) Naming.lookup("play-cards/1099/observablelist");
                } catch(Exception e) {
                    System.out.println(e);
                }
                observableRoomList = backup.getObservableList();
                System.out.println(observableRoomList);
            }
        }
        catch(Exception e) { System.out.println("erreur" + e);}
    }
}

