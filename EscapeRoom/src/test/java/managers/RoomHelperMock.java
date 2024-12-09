package managers;

import classes.Room;
import classes.enums.Level;
import utils.RoomHelper;

public class RoomHelperMock implements RoomHelper {

    @Override
    public Room createRoom() {
        return new Room("Mistery room", 45.0, Level.LOW);
    }
}
