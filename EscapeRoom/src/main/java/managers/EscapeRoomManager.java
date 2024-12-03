package managers;

import DAO.implementations.EscapeRoomDAOImpl;
import DAO.interfaces.EscapeRoomDAO;
import classes.EscapeRoom;
import utils.Entry;

import java.util.Optional;

public class EscapeRoomManager {

    public void createEscapeRoomIfNotPresent() {

        EscapeRoomDAO escapeRoomDAO = new EscapeRoomDAOImpl();
        Optional<EscapeRoom> escapeRoom = escapeRoomDAO.getEscapeRoomIfPresent();

        if (escapeRoom.isEmpty()) {
            System.out.println("To start, let's create a Escape Room. ");
            String name = Entry.readString("Give a name to the escape room");
            String CIF = Entry.readString("Give a CIF to the escape room");
            escapeRoomDAO.createEscapeRoom(new EscapeRoom(name, CIF));
            System.out.println("Escape room created");
        } else {
            System.out.println("\nWelcome to the Escape Room '" + escapeRoom.get().getName() + "'!!!!");
            System.out.println(escapeRoom.get());
        }

    }
}
