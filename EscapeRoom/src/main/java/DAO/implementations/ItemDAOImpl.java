package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.ItemDAO;
import classes.item.Item;
import classes.item.ItemFactory;
import classes.item.ItemFactoryImpl;
import classes.enums.Material;
import classes.item.implementations.Clue;
import classes.item.implementations.Decoration;
import classes.item.implementations.Enigma;
import connections.DbConnectionImpl;
import connections.callback.ParsingCallBack;
import connections.query.Query;
import connections.query.queryAttribute.DoubleQueryAttribute;
import connections.query.queryAttribute.IntQueryAttribute;
import connections.query.queryAttribute.QueryAttribute;
import connections.query.queryAttribute.StringQueryAttribute;
import connections.query.resultAttribute.Attribute;
import connections.query.resultAttribute.AttributeValue;
import connections.query.resultAttribute.ResultType;

import java.util.*;

public class ItemDAOImpl implements ItemDAO, ParsingCallBack<Item> {

    DbConnectionImpl dbConnection = DbConnectionImpl.getInstance();
    Parser<Item> parser = new Parser<>(this);
    ItemFactory itemFactory = new ItemFactoryImpl();

    private static final String ITEMID = "idenigma";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    private static final String MATERIAL = "material";
    private static final String QUANTITY = "quantity";
    private static final String THEME = "theme";

    @Override
    public void addEnigma(Enigma enigma, int roomId) {
        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new StringQueryAttribute(1, enigma.getName()));
        queryAttributeList.add(new DoubleQueryAttribute(2, enigma.getPrice()));
        queryAttributeList.add(new IntQueryAttribute(3, roomId));
        dbConnection.create(Query.CREATEENIGMA, queryAttributeList);
    }

    @Override
    public Enigma getEnigmaById(int itemId) {
       return null;
    }

    @Override
    public List<Enigma> getAllEnigmasByRoom(int roomId) {
        List<Enigma> enigmas = new ArrayList<>();

        List<QueryAttribute> queryAttributeList = List.of(new IntQueryAttribute(1, roomId));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute(ITEMID, ResultType.INT),
                new Attribute(NAME, ResultType.STRING),
                new Attribute(PRICE, ResultType.DOUBLE));

        List<HashSet<Attribute>> enigmaList = dbConnection.query(Query.GETENIGMABYROOM, queryAttributeList, outputAttributes);

        if (enigmaList.isEmpty()) return List.of();

        for (HashSet<Attribute> attributeValues: enigmaList) {
            Enigma enigma = itemFactory.createEnigma();
            parser.parseObject(enigma, attributeValues);
            enigmas.add(enigma);
        }

        return enigmas;
    }

    @Override
    public void deleteEnigma(int itemId) {
        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new IntQueryAttribute(1, itemId));
        dbConnection.delete(Query.DELETEENIGMA, queryAttributeList);
    }

    @Override
    public void addClue(Clue clue, int enigmaId) {
        List<QueryAttribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new StringQueryAttribute(1, clue.getName()));
        queryAttributeList.add(new DoubleQueryAttribute(2, clue.getPrice()));
        queryAttributeList.add(new StringQueryAttribute(3, clue.getTheme().name()));
        queryAttributeList.add(new IntQueryAttribute(4, enigmaId));
        dbConnection.create(Query.CREATECLUE, queryAttributeList);
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
        AttributeValue<String> attValue = attribute.getValue();
        if (attribute.getName().equals(NAME)) {
            object.setName(attValue.getValue());
        }
    }

    @Override
    public void onCallbackInt(Item object, Attribute attribute) {
        AttributeValue<Integer> attValue = attribute.getValue();
        switch (attribute.getName()) {
            case ITEMID -> object.setItemId(attValue.getValue());
            case QUANTITY -> {
                ((Decoration)object).setQuantity(attValue.getValue());
            }
        }
    }

    @Override
    public void onCallbackDouble(Item object, Attribute attribute) {
        AttributeValue<Double> attValue = attribute.getValue();
        if (attribute.getName().equals(PRICE)) {
            object.setPrice(attValue.getValue());
        }
    }

    public void onCallbackMaterial(Item object, Attribute attribute) {
        AttributeValue<Material> attValue = attribute.getValue();
        if (attribute.getName().equals(NAME)) {
            ((Decoration)object).setMaterial(attValue.getValue());
        }
    }
}
