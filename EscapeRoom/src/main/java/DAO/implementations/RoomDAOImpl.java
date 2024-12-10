package DAO.implementations;

import DAO.Parser;
import DAO.Query;
import DAO.interfaces.RoomDAO;
import classes.Room;
import classes.enums.Level;
import classes.enums.Material;
import classes.enums.Theme;
import classes.item.ItemFactory;
import classes.item.implementations.ItemFactoryImpl;
import connections.DbConnection;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import connections.callback.ParsingCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class RoomDAOImpl implements RoomDAO, ParsingCallback<Room> {

    DbConnection dbConnection;
    Parser<Room> parser = new Parser<>(this);
    ItemFactory itemFactory = new ItemFactoryImpl();

    private static final String IDROOM = "idroom";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String LEVEL = "level";

    public RoomDAOImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Boolean addRoom(Room room, Integer escapeRoomId) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(room.getName(), String.class));
        attributeList.add(new Attribute<>(room.getPrice(), Double.class));
        attributeList.add(new Attribute<>(room.getLevel().name(), String.class));
        attributeList.add(new Attribute<>(escapeRoomId, Integer.class));
        return dbConnection.create(Query.CREATEROOM, attributeList);
    }

    @Override
    public List<Room> getAllRoomsByEscapeRoom(Integer escapeRoomId) {
        List<Attribute> queryAttributeList = List.of(new Attribute<>(escapeRoomId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDROOM, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class),
                new Attribute<>(LEVEL, null, String.class));

        return this.getRooms(Query.GETROOMBYESCAPEROOM, queryAttributeList, outputAttributes);
    }

    @Override
    public Room getRoomById(Integer roomId) {
        List<Attribute> queryAttributeList = List.of(new Attribute<>(roomId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDROOM, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class),
                new Attribute<>(LEVEL, null, String.class));

        return this.getRooms(Query.GETROOMBYID, queryAttributeList, outputAttributes).getFirst();
    }

    @Override
    public List<Room> getData() {
        List<Attribute> queryAttributeList = List.of();
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDROOM, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class),
                new Attribute<>(LEVEL, null, String.class));

        return this.getRooms(Query.GETALLROOMS, queryAttributeList, outputAttributes);
    }

    private List<Room> getRooms(String query, List<Attribute> queryAttributes, List<Attribute> outputAttributes) {
        List<Room> rooms = new ArrayList<>();

        List<HashSet<Attribute>> roomList = dbConnection.query(query, queryAttributes, outputAttributes);


        if (roomList.isEmpty()) return List.of();

        for (HashSet<Attribute> attributeValues: roomList) {
            Room room = new Room();
            parser.parseObject(room, attributeValues);
            rooms.add(room);
        }

        return rooms;
    }

    @Override
    public void delete(Integer id) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(id, Integer.class));
        dbConnection.delete(Query.DELETEROOM, queryAttributeList);
    }

    @Override
    public void onCallbackString(Room object, Attribute<String> attribute) {
        switch (attribute.getName()) {
            case NAME -> object.setName(attribute.getValue());
            case LEVEL -> object.setLevel(Level.valueOf(attribute.getValue()));
        }
        if (attribute.getName().equals(NAME)) {
            object.setName(attribute.getValue());
        }
    }

    @Override
    public void onCallbackInteger(Room object, Attribute<Integer> attribute) {
        if (attribute.getName().equals(IDROOM)) {
            object.setIdRoom(attribute.getValue());
        }
    }

    @Override
    public void onCallbackDouble(Room object, Attribute<Double> attribute) {
        if (attribute.getName().equals(PRICE)) {
            object.setPrice(attribute.getValue());
        }
    }

    @Override
    public void onCallbackBoolean(Room object, Attribute<Boolean> attribute) {}
}
