package fr.playcards;

import fr.playcards.server.IServer;
import fr.playcards.server.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {
    public static IServer mainServer;

    static {
        try {
            mainServer = new Server("PlayCards");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] argv) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("play-cards/1099/connecting",mainServer);
            while(true){
                mainServer.refresh();
            }
        } catch(Exception e) { System.out.println("StartServer main Error : " + e);}
    }
}

