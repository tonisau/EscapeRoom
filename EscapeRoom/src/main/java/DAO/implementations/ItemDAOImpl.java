package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.ItemDAO;
import classes.EscapeRoom;
import classes.item.Item;
import classes.item.ItemFactory;
import classes.item.ItemType;
import classes.item.implementations.Clue;
import classes.item.implementations.Decoration;
import classes.item.implementations.Enigma;
import connections.DbConnection;
import connections.callback.ParsingCallBack;
import connections.query.Query;
import connections.query.queryAttribute.DoubleQueryAttribute;
import connections.query.queryAttribute.IntQueryAttribute;
import connections.query.queryAttribute.QueryAttribute;
import connections.query.queryAttribute.StringQueryAttribute;
import connections.query.resultAttribute.Attribute;
import connections.query.resultAttribute.ResultType;

import javax.security.auth.callback.Callback;
import java.util.*;

public class ItemDAOImpl implements ItemDAO, ParsingCallBack<Item> {

    DbConnection dbConnection = DbConnection.getInstance();
    Parser<Item> parser = new Parser<>(this);

    private static final String ITEMID = "itemId";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    @Override
    public void addEnigma(Enigma enigma, int roomId) {
        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new StringQueryAttribute(1, enigma.getName()));
        queryAttributeList.add(new DoubleQueryAttribute(2, enigma.getPrice()));
        queryAttributeList.add(new IntQueryAttribute(3, roomId));
        dbConnection.callCreate(Query.CREATEENIGMA, queryAttributeList);
    }

    @Override
    public Enigma getEnigmaById(int itemId) {
       return null;
    }

    @Override
    public List<Enigma> getAllEnigmasByRoom(int roomId) {
        List<QueryAttribute> queryAttributeList = List.of(new IntQueryAttribute(1, roomId));
        List<Attribute> resultAttributesList = Arrays.asList(
                new Attribute(ITEMID, ResultType.INT),
                new Attribute(NAME, ResultType.STRING),
                new Attribute(PRICE, ResultType.DOUBLE));

        List<HashSet<Attribute>> enigmaList = dbConnection.callQuery(Query.GETENIGMABYROOM, queryAttributeList, resultAttributesList);

        if (enigmaList.isEmpty()) return List.of();

        enigmaList.forEach(x -> {
            Enigma enigma = 
        });

        EscapeRoom escapeRoom = new EscapeRoom();
        parser.parseObject(escapeRoom, attributeValues);

        return Optional.of(escapeRoom);
    }

    @Override
    public void deleteEnigma(int itemId) {

    }

    @Override
    public void addClue(Clue clue, int enigmaId) {

    }

    @Override
    public Clue getClueById(int itemId) {
        return null;
    }

    @Override
    public List<Clue> getAllCluesByEnigma(int enigmaId) {
        return List.of();
    }

    @Override
    public void deleteClue(int itemId) {

    }

    @Override
    public void addDecoration(Decoration decoration, int roomId) {

    }

    @Override
    public Decoration getDecorationById(int itemId) {
        return null;
    }

    @Override
    public List<Decoration> getAllDecorationsByRoom(int roomId) {
        return List.of();
    }

    @Override
    public void deleteDecoration(int itemId) {

    }

    @Override
    public void onCallbackString(Item object, Attribute attribute) {

    }

    @Override
    public void onCallbackInt(Item object, Attribute attribute) {

    }
}
