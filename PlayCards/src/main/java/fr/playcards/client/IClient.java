package fr.playcards.client;

import fr.playcards.room.IRoom;

import java.io.Serializable;
import java.util.List;

public interface IClient extends Serializable {

    public void refresh();
    public List<IRoom> getObservableRoomList();

}
