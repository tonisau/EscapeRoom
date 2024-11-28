package DAO.implementations;

import DAO.interfaces.EscapeRoomDAO;
import classes.EscapeRoom;
import connections.DbConnection;
import connections.query.Query;
import connections.query.queryAttribute.QueryAttribute;
import connections.query.queryAttribute.StringQueryAttribute;
import connections.query.resultAttribute.Attribute;
import connections.query.resultAttribute.ResultType;

import java.util.*;

public class EscapeRoomDAOImpl implements EscapeRoomDAO {

    DbConnection dbConnection;
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
        List<Attribute> attributesList = Arrays.asList(
                new Attribute(IDESCAPEROOM, ResultType.INT),
                new Attribute(NAME, ResultType.STRING),
                new Attribute(CIF, ResultType.STRING));

        List<HashMap<String, Object>> escapeRoomsList = dbConnection.callQuery(Query.GETESCAPEROOM, queryAttributeList, attributesList);

        if (escapeRoomsList.isEmpty()) return Optional.empty();

        HashMap<String, Object> attributes = escapeRoomsList.getFirst();
        if (attributes.isEmpty()) return Optional.empty();

        EscapeRoom escape = new EscapeRoom();

        for (Attribute attribute: attributesList) {
            String key = attribute.getName();
            if (attributes.containsKey(key)) {
                switch (attribute.getType()) {
                    case STRING -> {
                        String att = String.valueOf(attributes.get(key));
                        switch (attribute.getName()) {
                            case NAME -> escape.setName(att);
                            case CIF -> escape.setCif(att);
                        }
                    }
                    case INT -> {
                        int att = (int) attributes.get(key);
                        escape.setIdEscaperoom(att);
                    }
                    default -> {
                    }
                }
            }
        }

        return Optional.of(escape);
    }
}
