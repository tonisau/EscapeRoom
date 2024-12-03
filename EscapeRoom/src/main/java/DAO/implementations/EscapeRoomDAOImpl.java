package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.EscapeRoomDAO;
import classes.EscapeRoom;
import classes.enums.Material;
import connections.DbConnection;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import connections.attribute.queryAttribute.IntQueryAttribute;
import connections.callback.ParsingCallback;
import connections.attribute.Query;
import connections.attribute.queryAttribute.QueryAttribute;
import connections.attribute.queryAttribute.StringQueryAttribute;

import java.util.*;

public class EscapeRoomDAOImpl implements EscapeRoomDAO, ParsingCallback<EscapeRoom> {

    DbConnection dbConnection = DbConnectionImpl.getInstance();
    Parser<EscapeRoom> parser = new Parser<>(this);

    private static final String IDESCAPEROOM = "idEscapeRoom";
    private static final String NAME = "name";
    private static final String CIF = "cif";

    public EscapeRoomDAOImpl() {}

    @Override
    public void createEscapeRoom(EscapeRoom escapeRoom) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<String>(escapeRoom.getName(), String.class));
        attributeList.add(new Attribute<String>(escapeRoom.getCif(), String.class));
        dbConnection.create(Query.CREATEESCAPEROOM, attributeList);
    }

    @Override
    public Optional<EscapeRoom> getEscapeRoomIfPresent() {

        List<Attribute> queryAttributeList = new ArrayList<>();

        List<Attribute> outputAttributesList = Arrays.asList(
                new Attribute<Integer>(IDESCAPEROOM, null, Integer.class),
                new Attribute<String>(NAME, null, String.class),
                new Attribute<String>(CIF, null, String.class));


        List<HashSet<Attribute>> escapeRoomsList = dbConnection.query(Query.GETESCAPEROOM, queryAttributeList, outputAttributesList);

        if (escapeRoomsList.isEmpty()) return Optional.empty();

        HashSet<Attribute> attributeValues = escapeRoomsList.getFirst();
        if (attributeValues.isEmpty()) return Optional.empty();

        EscapeRoom escapeRoom = new EscapeRoom();
        parser.parseObject(escapeRoom, attributeValues);

        return Optional.of(escapeRoom);
    }

    public void deleteEscapeRoom(int escapeRoomId) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(escapeRoomId, Integer.class));
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
    public void onCallbackInt(EscapeRoom escapeRoom, Attribute<Integer> attribute) {
        if (attribute.getName().equals(IDESCAPEROOM)) {
            escapeRoom.setIdEscaperoom(attribute.getValue());
        }
    }

    @Override
    public void onCallbackDouble(EscapeRoom object, Attribute<Double> attribute) {}

    @Override
    public void onCallbackMaterial(EscapeRoom object, Attribute<Material> attribute) {}
}
