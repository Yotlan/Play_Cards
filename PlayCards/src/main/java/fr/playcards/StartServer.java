package fr.playcards;

//Import part
import fr.playcards.server.IServer;
import fr.playcards.server.Server;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
@author Yotlan LE CROM

This class have for goal to initialize and launch the server with the necessary bindings for the clients who want to
access the server
 */

public class StartServer {

    //Initialize mainServer
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
            //Create registry for an access to the mainServer for all clients on default port
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("play-cards/1099/connecting",mainServer);
            while(true){
                mainServer.refreshRoom();
            }
        } catch(Exception e) { System.out.println("StartServer main Error : " + e);}
    }
}

