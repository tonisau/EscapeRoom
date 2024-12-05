package DAO.interfaces;

import classes.Room;
import classes.item.implementations.Clue;
import exceptions.NoRoomsException;

import java.util.List;

public interface RoomDAO extends DAO<Room> {
    void addRoom(Room room, Integer escapeRoomId);
    List<Room> getAllRoomsByEscapeRoom(Integer escapeRoomId);
    Room getRoomById(Integer roomId);
}
