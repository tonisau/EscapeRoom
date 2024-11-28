package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.EscapeRoomDAO;
import classes.EscapeRoom;
import connections.DbConnection;
import connections.callback.CallBack;
import connections.query.Query;
import connections.query.queryAttribute.QueryAttribute;
import connections.query.queryAttribute.StringQueryAttribute;
import connections.query.resultAttribute.Attribute;
import connections.query.resultAttribute.AttributeValue;
import connections.query.resultAttribute.ResultType;

import java.util.*;

public class EscapeRoomDAOImpl implements EscapeRoomDAO, CallBack<EscapeRoom> {

    DbConnection dbConnection;
    Parser<EscapeRoom> parser = new Parser<>(this);

    private static final String IDESCAPEROOM = "idEscapeRoom";
    private static final String NAME = "name";
    private static final String CIF = "cif";

    public EscapeRoomDAOImpl() {
        dbConnection = new DbConnection();
    }

    @Override
    public void createEscapeRoom(EscapeRoom escapeRoom) {
        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new StringQueryAttribute(1, escapeRoom.getName()));
        queryAttributeList.add(new StringQueryAttribute(2, escapeRoom.getCif()));
        dbConnection.callCreate(Query.CREATEESCAPEROOM, queryAttributeList);
    }

    @Override
    public Optional<EscapeRoom> getEscapeRoomIfPresent() {

        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        List<Attribute> resultAttributesList = Arrays.asList(
                new Attribute(IDESCAPEROOM, ResultType.INT),
                new Attribute(NAME, ResultType.STRING),
                new Attribute(CIF, ResultType.STRING));

        List<HashMap<String, Attribute>> escapeRoomsList = dbConnection.callQuery(Query.GETESCAPEROOM, queryAttributeList, resultAttributesList);

        if (escapeRoomsList.isEmpty()) return Optional.empty();

        HashMap<String, Attribute> values = escapeRoomsList.getFirst();
        if (values.isEmpty()) return Optional.empty();

        EscapeRoom escape = new EscapeRoom();
        parser.parseObject(escape, values);

        return Optional.of(escape);
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
}
