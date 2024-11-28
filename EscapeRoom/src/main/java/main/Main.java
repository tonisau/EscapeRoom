package main;

import classes.EscapeRoom;
import connections.DbConnection;
import managers.ConnectionManager;
import managers.EscapeRoomManager;
import managers.MainManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        connectionManager.createEscapeRoom(new EscapeRoom("Escape name 2", "34356543S"));
        List<EscapeRoom> escapeRoomList = connectionManager.getAllEscapeRooms();

        escapeRoomList.forEach(System.out::println);

        MainManager mainManager = new MainManager();
        mainManager.start();
    }
}