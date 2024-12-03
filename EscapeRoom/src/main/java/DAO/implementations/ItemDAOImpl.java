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
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(enigma.getName(), String.class));
        attributeList.add(new Attribute<>(enigma.getPrice(), Double.class));
        attributeList.add(new Attribute<>(roomId, Integer.class));
        dbConnection.create(Query.CREATEENIGMA, attributeList);
    }

    @Override
    public Enigma getEnigmaById(int itemId) {
       return null;
    }

    @Override
    public List<Enigma> getAllEnigmasByRoom(int roomId) {
        List<Enigma> enigmas = new ArrayList<>();

        List<Attribute> queryAttributeList = List.of(new Attribute<>(roomId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(ITEMID, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class));

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
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(itemId, Integer.class));
        dbConnection.delete(Query.DELETEENIGMA, queryAttributeList);
    }

    @Override
    public void addClue(Clue clue, int enigmaId) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(clue.getName(), String.class));
        queryAttributeList.add(new Attribute(clue.getPrice(), Double.class));
        queryAttributeList.add(new Attribute(clue.getTheme().name(), String.class));
        queryAttributeList.add(new Attribute(enigmaId, Integer.class));
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

    @Override
    public void onCallbackBoolean(Item object, Attribute<Boolean> attribute) {

    }
}
