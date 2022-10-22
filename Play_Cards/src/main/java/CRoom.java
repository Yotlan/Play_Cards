import java.rmi.*; import java.rmi.registry.*;

public class CRoom {
    public static void main(String[] argv) {
        try {
            IRoom room =
                    (IRoom) Naming.lookup(
                            "play-cards/1099/createroom");
            System.out.println(room.createRoom("Hello World !"));
        } catch (Exception e) {
            System.out.println("erreur" + e);
        }
    }
}
