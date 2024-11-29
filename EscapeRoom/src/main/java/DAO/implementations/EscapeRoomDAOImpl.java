package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.EscapeRoomDAO;
import classes.EscapeRoom;
import connections.DbConnectionImpl;
import connections.callback.ParsingCallBack;
import connections.query.Query;
import connections.query.queryAttribute.QueryAttribute;
import connections.query.queryAttribute.StringQueryAttribute;
import connections.query.resultAttribute.Attribute;
import connections.query.resultAttribute.AttributeValue;
import connections.query.resultAttribute.ResultType;

import java.util.*;

public class EscapeRoomDAOImpl implements EscapeRoomDAO, ParsingCallBack<EscapeRoom> {

    DbConnectionImpl dbConnection = DbConnectionImpl.getInstance();
    Parser<EscapeRoom> parser = new Parser<>(this);

    private static final String IDESCAPEROOM = "idEscapeRoom";
    private static final String NAME = "name";
    private static final String CIF = "cif";

    public EscapeRoomDAOImpl() {}

    @Override
    public void createEscapeRoom(EscapeRoom escapeRoom) {
        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new StringQueryAttribute(1, escapeRoom.getName()));
        queryAttributeList.add(new StringQueryAttribute(2, escapeRoom.getCif()));
        dbConnection.create(Query.CREATEESCAPEROOM, queryAttributeList);
    }

    @Override
    public Optional<EscapeRoom> getEscapeRoomIfPresent() {

        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute(IDESCAPEROOM, ResultType.INT),
                new Attribute(NAME, ResultType.STRING),
                new Attribute(CIF, ResultType.STRING));

        List<HashSet<Attribute>> escapeRoomsList = dbConnection.query(Query.GETESCAPEROOM, queryAttributeList, outputAttributes);

        if (escapeRoomsList.isEmpty()) return Optional.empty();

        HashSet<Attribute> attributeValues = escapeRoomsList.getFirst();
        if (attributeValues.isEmpty()) return Optional.empty();

        EscapeRoom escapeRoom = new EscapeRoom();
        parser.parseObject(escapeRoom, attributeValues);

        return Optional.of(escapeRoom);
    }

    @Override
    public void onCallbackString(EscapeRoom escapeRoom, Attribute attribute) {
        AttributeValue<String> attValue = attribute.getValue();
        switch (attribute.getName()) {
            case NAME -> escapeRoom.setName(attValue.getValue());
            case CIF -> escapeRoom.setCif(attValue.getValue());
        }
    }

    @Override
    public void onCallbackInt(EscapeRoom escapeRoom, Attribute attribute) {
        AttributeValue<Integer> attValue = attribute.getValue();
        if (attribute.getName().equals(IDESCAPEROOM)) {
            escapeRoom.setIdEscaperoom(attValue.getValue());
        }
    }

    @Override
    public void onCallbackDouble(EscapeRoom object, Attribute attribute) {}
}
