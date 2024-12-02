package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.ItemDAO;
import classes.item.Item;
import classes.item.ItemFactory;
import classes.item.implementations.ItemFactoryImpl;
import classes.enums.Material;
import classes.item.implementations.Clue;
import classes.item.implementations.Decoration;
import classes.item.implementations.Enigma;
import connections.DbConnectionImpl;
import connections.attribute.Query;
import connections.attribute.outputAttribute.AttributeValue;
import connections.attribute.outputAttribute.OutputAttribute;
import connections.attribute.outputAttribute.OutputType;
import connections.attribute.queryAttribute.IntQueryAttribute;
import connections.attribute.queryAttribute.QueryAttribute;
import connections.attribute.queryAttribute.StringQueryAttribute;
import connections.callback.ParsingCallback;
import connections.query.queryAttribute.DoubleQueryAttribute;

import java.util.*;

public class ItemDAOImpl implements ItemDAO, ParsingCallback<Item> {

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
        List<OutputAttribute> outputAttributes = Arrays.asList(
                new OutputAttribute(ITEMID, OutputType.INT),
                new OutputAttribute(NAME, OutputType.STRING),
                new OutputAttribute(PRICE, OutputType.DOUBLE));

        List<HashSet<OutputAttribute>> enigmaList = dbConnection.query(Query.GETENIGMABYROOM, queryAttributeList, outputAttributes);

        if (enigmaList.isEmpty()) return List.of();

        for (HashSet<OutputAttribute> attributeValues: enigmaList) {
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
    public void onCallbackString(Item object, OutputAttribute attribute) {
        AttributeValue<String> attValue = attribute.getValue();
        if (attribute.getName().equals(NAME)) {
            object.setName(attValue.getValue());
        }
    }

    @Override
    public void onCallbackInt(Item object, OutputAttribute attribute) {
        AttributeValue<Integer> attValue = attribute.getValue();
        switch (attribute.getName()) {
            case ITEMID -> object.setItemId(attValue.getValue());
            case QUANTITY -> {
                ((Decoration)object).setQuantity(attValue.getValue());
            }
        }
    }

    @Override
    public void onCallbackDouble(Item object, OutputAttribute attribute) {
        AttributeValue<Double> attValue = attribute.getValue();
        if (attribute.getName().equals(PRICE)) {
            object.setPrice(attValue.getValue());
        }
    }

    @Override
    public void onCallbackMaterial(Item object, OutputAttribute attribute) {
        AttributeValue<Material> attValue = attribute.getValue();
        if (attribute.getName().equals(NAME)) {
            ((Decoration)object).setMaterial(attValue.getValue());
        }
    }
}
