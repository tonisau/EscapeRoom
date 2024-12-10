package DAO.implementations;

import DAO.interfaces.RoomDAO;
import classes.Room;
import classes.enums.Level;
import connections.attribute.Attribute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

public class RoomDAOImplTest {

    RoomDAO roomDAO;
    DBConnectionMock dbConnection;

    @BeforeEach
    void init() {
        this.dbConnection = new DBConnectionMock();
        this.roomDAO = new RoomDAOImpl(dbConnection);
    }

    @ParameterizedTest
    @CsvSource({"Room,123.50,HIGH,4", "Charles,12.0,LOW,5"})
    void givenRoomDAO_whenAddRoom_ThenQueryAttributesAsExpected(ArgumentsAccessor argumentsAccessor) {
        String name = argumentsAccessor.getString(0);
        Double price = argumentsAccessor.get(1, Double.class);
        Level level = Level.valueOf(argumentsAccessor.get(2, String.class));
        Integer escapeRoomId = argumentsAccessor.get(3, Integer.class);
        Room room = new Room(name, price, level);

        roomDAO.addRoom(room, escapeRoomId);
        Assertions.assertEquals(dbConnection.queryAttributes.size(), 4);
        Assertions.assertEquals(dbConnection.queryAttributes.getFirst(), new Attribute<String>(room.getName(), String.class));
        Assertions.assertEquals(dbConnection.queryAttributes.get(1), new Attribute<Double>(room.getPrice(), Double.class));
        Assertions.assertEquals(dbConnection.queryAttributes.get(2), new Attribute<String>(room.getLevel().name(), String.class));
        Assertions.assertEquals(dbConnection.queryAttributes.get(3), new Attribute<Integer>(escapeRoomId, Integer.class));
    }
}
