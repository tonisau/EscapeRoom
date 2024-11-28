package main;

import DAO.EscapeRoomDAO;
import DAO.implementation.EscapeRoomDAOImpl;
import classes.EscapeRoom;
import managers.MainManager;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        EscapeRoomDAO escapeRoomDAO = new EscapeRoomDAOImpl();
        Optional<EscapeRoom> escapeRoom = escapeRoomDAO.getEscapeRoomIfPresent();
        if (escapeRoom.isEmpty()) {
            escapeRoomDAO.createEscapeRoom(new EscapeRoom("Escape name", "545465422e"));
            System.out.println("Escape room created");
        } else {
            System.out.println("Escape Room already exists: " + escapeRoom.get());
        }

        MainManager mainManager = new MainManager();
        mainManager.start();
    }
}