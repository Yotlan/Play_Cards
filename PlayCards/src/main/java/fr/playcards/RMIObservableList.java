package fr.playcards;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIObservableList<E> extends UnicastRemoteObject implements IRMIObservableList {

    public List<E> observableList;

    public RMIObservableList(List<E> observableList) throws RemoteException {
        this.observableList = observableList;
    }

    public List<E> getObservableList() throws RemoteException {
        return this.observableList;
    }
}
