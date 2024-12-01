package managers;

import DAO.interfaces.implementations.DAORoomImpl;
import classes.Room;
import classes.enums.*;
import exceptions.NoRoomsException;
import utils.Entry;

public class RoomManager {

    private static RoomManager instance;
    private final DAORoomImpl daoRoom;

    public RoomManager(){
        this.daoRoom = new DAORoomImpl();
    }

    public static RoomManager getInstance() {
        if (instance == null) {
            instance = new RoomManager();
        }
        return instance;
    }

    public void createRoom(){
        int idroom=0;
        String name="";
        double price=0;
        int level=0;
        Level choosenLevel=null;
        Room newRoom=null;

        name = Entry.readString("Introduce the name of the room:");
        price = Entry.readDouble("Introduce the price of the room:");
        do {
            level = Entry.readInt("Choose the difficulty: 1, 2 or 3");
            if(level < 1 || level > 3) {
                System.out.println("Please, choose a valid option: 1, 2, 3.");
            } else {
                Level chosenLevel = Level.findByValue(level);
            }
        } while(level < 1 || level > 3);

        newRoom=new Room(name,price, choosenLevel);
        this.daoRoom.add(newRoom);
    }
}
