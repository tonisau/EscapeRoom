package managers;

import classes.Room;
import classes.enums.Level;
import utils.RoomHelper;

public class RoomHelperMock implements RoomHelper {

    Room room;

    public RoomHelperMock(Room room) {
        this.room = room;
    }

    @Override
    public Room createRoom() {
        return room;
    }
}
