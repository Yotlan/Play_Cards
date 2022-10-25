package fr.playcards;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IRMIObservableList<E> extends Remote {
    public List<E> getObservableList() throws RemoteException;
}
