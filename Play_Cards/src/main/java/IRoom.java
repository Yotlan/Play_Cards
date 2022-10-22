import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote {
    public String createRoom(String msg) throws RemoteException;
}
