import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class InitSRoom {
    public static void main(String[] argv) {
        try {
            LocateRegistry.createRegistry(1099);
            SRoom room = new SRoom();
            Naming.bind(
                    "play-cards/1099/createroom",
                    room);
        }
        catch(Exception e) { System.out.println("erreur" + e);}
    }
}
