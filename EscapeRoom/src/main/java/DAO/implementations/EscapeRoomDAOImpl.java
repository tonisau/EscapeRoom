package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.EscapeRoomDAO;
import classes.EscapeRoom;
import connections.DbConnection;
import connections.DbConnectionImpl;
import connections.attribute.queryAttribute.IntQueryAttribute;
import connections.callback.ParsingCallback;
import connections.attribute.Query;
import connections.attribute.queryAttribute.QueryAttribute;
import connections.attribute.queryAttribute.StringQueryAttribute;
import connections.attribute.outputAttribute.OutputAttribute;
import connections.attribute.outputAttribute.AttributeValue;
import connections.attribute.outputAttribute.ResultType;

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
        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new StringQueryAttribute(1, escapeRoom.getName()));
        queryAttributeList.add(new StringQueryAttribute(2, escapeRoom.getCif()));
        dbConnection.create(Query.CREATEESCAPEROOM, queryAttributeList);
    }

    @Override
    public Optional<EscapeRoom> getEscapeRoomIfPresent() {

        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        List<OutputAttribute> resultAttributesList = Arrays.asList(
                new OutputAttribute(IDESCAPEROOM, ResultType.INT),
                new OutputAttribute(NAME, ResultType.STRING),
                new OutputAttribute(CIF, ResultType.STRING));

        List<HashSet<OutputAttribute>> escapeRoomsList = dbConnection.query(Query.GETESCAPEROOM, queryAttributeList, resultAttributesList);

        if (escapeRoomsList.isEmpty()) return Optional.empty();

        HashSet<OutputAttribute> attributeValues = escapeRoomsList.getFirst();
        if (attributeValues.isEmpty()) return Optional.empty();

        EscapeRoom escapeRoom = new EscapeRoom();
        parser.parseObject(escapeRoom, attributeValues);

        return Optional.of(escapeRoom);
    }

    public void deleteEscapeRoom(int escapeRoomId) {
        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new IntQueryAttribute(1, escapeRoomId));
        dbConnection.delete(Query.DELETEESCAPEROOM, queryAttributeList);
    }

    @Override
    public void onCallbackString(EscapeRoom escapeRoom, OutputAttribute attribute) {
        AttributeValue<String> attValue = attribute.getValue();
        switch (attribute.getName()) {
            case NAME -> escapeRoom.setName(attValue.getValue());
            case CIF -> escapeRoom.setCif(attValue.getValue());
        }
    }

    @Override
    public void onCallbackInt(EscapeRoom escapeRoom, OutputAttribute attribute) {
        AttributeValue<Integer> attValue = attribute.getValue();
        if (attribute.getName().equals(IDESCAPEROOM)) {
            escapeRoom.setIdEscaperoom(attValue.getValue());
        }
    }
}
