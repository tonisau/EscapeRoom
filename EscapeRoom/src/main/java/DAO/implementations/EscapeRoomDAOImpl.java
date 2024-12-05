package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.EscapeRoomDAO;
import classes.EscapeRoom;
import connections.DbConnection;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import connections.callback.ParsingCallback;
import DAO.Query;

import java.util.*;

public class EscapeRoomDAOImpl implements EscapeRoomDAO, ParsingCallback<EscapeRoom> {

    DbConnection dbConnection = DbConnectionImpl.getInstance();
    Parser<EscapeRoom> parser = new Parser<>(this);

    private static final String IDESCAPEROOM = "idEscapeRoom";
    private static final String NAME = "name";
    private static final String CIF = "cif";

    public EscapeRoomDAOImpl() {}

    @Override
    public void add(EscapeRoom escapeRoom) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(escapeRoom.getName(), String.class));
        attributeList.add(new Attribute<>(escapeRoom.getCif(), String.class));
        dbConnection.create(Query.CREATEESCAPEROOM, attributeList);
    }

    @Override
    public List<EscapeRoom> getData() {
        List<Attribute> queryAttributeList = new ArrayList<>();

        List<Attribute> outputAttributesList = Arrays.asList(
                new Attribute<>(IDESCAPEROOM, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(CIF, null, String.class));

        List<HashSet<Attribute>> escapeRoomsList = dbConnection.query(Query.GETESCAPEROOM, queryAttributeList, outputAttributesList);

        if (escapeRoomsList.isEmpty()) return List.of();

        HashSet<Attribute> attributeValues = escapeRoomsList.getFirst();
        if (attributeValues.isEmpty()) return List.of();

        EscapeRoom escapeRoom = new EscapeRoom();
        parser.parseObject(escapeRoom, attributeValues);

        return List.of(escapeRoom);
    }

    @Override
    public void delete(Integer id) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(id, Integer.class));
        dbConnection.delete(Query.DELETEESCAPEROOM, queryAttributeList);
    }

    @Override
    public void onCallbackString(EscapeRoom escapeRoom, Attribute<String> attribute) {
        switch (attribute.getName()) {
            case NAME -> escapeRoom.setName(attribute.getValue());
            case CIF -> escapeRoom.setCif(attribute.getValue());
        }
    }

    @Override
    public void onCallbackInteger(EscapeRoom escapeRoom, Attribute<Integer> attribute) {
        if (attribute.getName().equals(IDESCAPEROOM)) {
            escapeRoom.setIdEscaperoom(attribute.getValue());
        }
    }

    @Override
    public void onCallbackDouble(EscapeRoom object, Attribute<Double> attribute) {}

    @Override
    public void onCallbackBoolean(EscapeRoom object, Attribute<Boolean> attribute) {}
}
