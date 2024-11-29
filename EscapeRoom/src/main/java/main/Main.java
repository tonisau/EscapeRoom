package main;

import DAO.interfaces.EscapeRoomDAO;
import DAO.implementations.EscapeRoomDAOImpl;
import classes.EscapeRoom;
import managers.MainManager;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        MainManager mainManager = new MainManager();
        mainManager.start();
    }
}