package main;

import connections.DbConnection;
import managers.EscapeRoomManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DbConnection dbconnection = new DbConnection();
        Connection connection = dbconnection.getConnection();
        if (connection !=null){
            System.out.println("Connection successful");

            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO escaperoom (name, cif) VALUES (?, ?)");
                stmt.setString(1, "My escape");
                stmt.setString(2, "34546340u34069d");
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            EscapeRoomManager escapeRoomManager = new EscapeRoomManager();
            escapeRoomManager.start();
            }
    }
}