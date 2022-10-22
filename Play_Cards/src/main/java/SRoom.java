import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SRoom extends UnicastRemoteObject implements IRoom{

    protected SRoom() throws RemoteException {
    }

    @Override
    public String createRoom(String msg) throws RemoteException {
        return msg;
    }
}
