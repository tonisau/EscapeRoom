package managers;

import DAO.interfaces.RoomDAO;
import classes.Room;

import java.util.List;

public class RoomDAOMock implements RoomDAO {

    Boolean success = false;
    Room room;
    Integer escapeRoomId;

    @Override
    public Boolean addRoom(Room room, Integer escapeRoomId) {
        this.room = room;
        this.escapeRoomId = escapeRoomId;
        return success;
    }

    @Override
    public List<Room> getAllRoomsByEscapeRoom(Integer escapeRoomId) {
        return List.of();
    }

    @Override
    public Room getRoomById(Integer roomId) {
        return null;
    }

    @Override
    public List<Room> getData() {
        return List.of();
    }

    @Override
    public void delete(Integer id) {

    }
}
