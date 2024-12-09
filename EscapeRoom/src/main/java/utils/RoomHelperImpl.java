package utils;

import classes.Room;
import classes.enums.Level;

public class RoomHelperImpl implements RoomHelper {

    public Room createRoom() {
        String name = Entry.readString("Give a name for the room");
        Double price = Entry.readDouble("Enter a price for the room");
        Level level = Entry.readLevel("Enter a level for the room (Low/Medium/High)");
        return new Room(name, price, level);
    }
}
