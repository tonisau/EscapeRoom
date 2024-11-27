package main;

import connections.DbConnection;
import managers.EscapeRoomManager;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DbConnection dbconnection = new DbConnection();
        Connection connection = dbconnection.getConnection();
        if (connection !=null){
            System.out.println("Connection successful");
        }

        EscapeRoomManager escapeRoomManager = new EscapeRoomManager();
        escapeRoomManager.start();
    }
}