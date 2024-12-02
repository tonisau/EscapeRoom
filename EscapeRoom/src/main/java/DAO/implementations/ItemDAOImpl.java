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
import connections.attribute.Attribute;
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
       /* List<QueryAttribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new StringQueryAttribute(1, enigma.getName()));
        queryAttributeList.add(new DoubleQueryAttribute(2, enigma.getPrice()));
        queryAttributeList.add(new IntQueryAttribute(3, roomId));
        dbConnection.create(Query.CREATEENIGMA, queryAttributeList);*/

        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<String>(enigma.getName(), String.class));
        attributeList.add(new Attribute<Double>(enigma.getPrice(), Double.class));
        attributeList.add(new Attribute<Integer>(roomId, Integer.class));
        dbConnection.createWithGenerics(Query.CREATEENIGMA, attributeList);
    }

    @Override
    public Enigma getEnigmaById(int itemId) {
       return null;
    }

    @Override
    public List<Enigma> getAllEnigmasByRoom(int roomId) {
        List<Enigma> enigmas = new ArrayList<>();

        List<Attribute> queryAttributeList = List.of(new Attribute<Integer>(roomId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<Integer>(ITEMID, null, Integer.class),
                new Attribute<String>(NAME, null, String.class),
                new Attribute<Double>(PRICE, null, Double.class));

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
    public void onCallbackString(Item object, Attribute<String> attribute) {
        if (attribute.getName().equals(NAME)) {
            object.setName(attribute.getValue());
        }
    }

    @Override
    public void onCallbackInt(Item object, Attribute<Integer> attribute) {
        switch (attribute.getName()) {
            case ITEMID -> object.setItemId(attribute.getValue());
            case QUANTITY -> {
                ((Decoration)object).setQuantity(attribute.getValue());
            }
        }
    }

    @Override
    public void onCallbackDouble(Item object, Attribute<Double> attribute) {
        if (attribute.getName().equals(PRICE)) {
            object.setPrice(attribute.getValue());
        }
    }

    @Override
    public void onCallbackMaterial(Item object, Attribute<Material> attribute) {
        if (attribute.getName().equals(NAME)) {
            ((Decoration)object).setMaterial(attribute.getValue());
        }
    }
}
