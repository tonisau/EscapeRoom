package DAO.implementations;

import DAO.interfaces.EscapeRoomDAO;
import classes.EscapeRoom;

import connections.attribute.Attribute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class EscapeRoomDAOImplTest {

    EscapeRoomDAO escapeRoomDAO;
    EscapeRoom escapeRoom;
    DBConnectionMock dbConnection;

    @BeforeEach
    void init() {
        this.dbConnection = new DBConnectionMock();
        this.escapeRoomDAO = new EscapeRoomDAOImpl(dbConnection);
        this.escapeRoom = new EscapeRoom("Escape name", "4567f");
    }

    @Test
    void givenEscapeRoomDAO_whenAddEscapeRoom_ThenQueryAttributesAsExpected() {
        escapeRoomDAO.add(escapeRoom);
        Assertions.assertEquals(dbConnection.queryAttributes.size(), 2);
        Assertions.assertEquals(dbConnection.queryAttributes.getFirst(), new Attribute<String>(this.escapeRoom.getName(), String.class));
        Assertions.assertEquals(dbConnection.queryAttributes.get(1), new Attribute<String>(this.escapeRoom.getCif(), String.class));
    }
}