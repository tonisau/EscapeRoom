package DAO.interfaces;

import classes.Room;
import exceptions.NoRoomsException;

public interface RoomDAO extends DAO<Room> {

    Room findRoom() throws NoRoomsException;
    void removeRoom(Room room);
}
