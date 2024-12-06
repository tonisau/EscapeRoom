package managers;

import DAO.implementations.EscapeRoomDAOImpl;
import DAO.interfaces.EscapeRoomDAO;
import classes.EscapeRoom;
import utils.Entry;

import java.util.List;

public class EscapeRoomManager {

    private static EscapeRoomManager instance;
    EscapeRoomDAO escapeRoomDAO;
    EscapeRoom escapeRoom;

    private EscapeRoomManager(){
        this.escapeRoomDAO = new EscapeRoomDAOImpl();
    }

    public static EscapeRoomManager getInstance(){
        if (instance == null) instance = new EscapeRoomManager();
        return instance;
    }

    public void createEscapeRoomIfNotPresent() {
        List<EscapeRoom> escapeRoomList = escapeRoomDAO.getData();

        if (escapeRoomList.isEmpty()) {
            System.out.println("To start, let's create a Escape Room. ");
            String name = Entry.readString("Give a name to the escape room");
            String CIF = Entry.readString("Give a CIF to the escape room");
            escapeRoomDAO.add(new EscapeRoom(name, CIF));
            System.out.println("Escape room created");
        } else {
            escapeRoom = escapeRoomList.getFirst();
            System.out.println("\nWelcome to the Escape Room '" + escapeRoom.getName() + "'!!!!");
            System.out.println(escapeRoom);
        }
    }

    public EscapeRoom getEscapeRoom() {
        if (this.escapeRoom != null) return escapeRoom;
        List<EscapeRoom> escapeRoomList = escapeRoomDAO.getData();
        escapeRoom = escapeRoomList.getFirst();
        return escapeRoom;
    }
}
