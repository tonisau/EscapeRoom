package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.EnigmaDAO;
import classes.enums.Material;
import classes.enums.Theme;
import classes.item.ItemFactory;
import classes.item.implementations.Enigma;
import classes.item.implementations.ItemFactoryImpl;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import DAO.Query;
import connections.callback.ParsingCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class EnigmaDAOImpl implements EnigmaDAO, ParsingCallback<Enigma> {

    DbConnectionImpl dbConnection = DbConnectionImpl.getInstance();
    Parser<Enigma> parser = new Parser<>(this);
    ItemFactory itemFactory = new ItemFactoryImpl();

    private static final String IDENIGMA = "idenigma";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    @Override
    public void addEnigma(Enigma enigma, Integer roomId) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(enigma.getName(), String.class));
        attributeList.add(new Attribute<>(enigma.getPrice(), Double.class));
        attributeList.add(new Attribute<>(roomId, Integer.class));
        dbConnection.create(Query.CREATEENIGMA, attributeList);
    }

    @Override
    public List<Enigma> getAllEnigmasByRoom(Integer roomId) {
        List<Enigma> enigmas = new ArrayList<>();

        List<Attribute> queryAttributeList = List.of(new Attribute<>(roomId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDENIGMA, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class));

        return this.getEngimas(Query.GETENIGMABYROOM, queryAttributeList, outputAttributes);
    }

    @Override
    public Enigma getEnigmaById(Integer itemId) {
        List<Enigma> enigmas = new ArrayList<>();

        List<Attribute> queryAttributeList = List.of(new Attribute<>(itemId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDENIGMA, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class));

        return this.getEngimas(Query.GETENIGMABYID, queryAttributeList, outputAttributes).getFirst();
    }

    @Override
    public List<Enigma> getData() {
        List<Attribute> queryAttributeList = List.of();
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDENIGMA, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class));

        return this.getEngimas(Query.GETALLENIGMAS, queryAttributeList, outputAttributes);
    }

    private List<Enigma> getEngimas(String query, List<Attribute> queryAttributes, List<Attribute> outputAttributes) {

        List<Enigma> enigmas = new ArrayList<>();

        List<HashSet<Attribute>> enigmaList = dbConnection.query(query, queryAttributes, outputAttributes);

        if (enigmaList.isEmpty()) return List.of();

        for (HashSet<Attribute> attributeValues: enigmaList) {
            Enigma enigma = itemFactory.createEnigma();
            parser.parseObject(enigma, attributeValues);
            enigmas.add(enigma);
        }

        return enigmas;
    }

    @Override
    public void delete(Integer id) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(id, Integer.class));
        dbConnection.delete(Query.DELETEENIGMA, queryAttributeList);
    }

    @Override
    public void onCallbackString(Enigma object, Attribute<String> attribute) {
        if (attribute.getName().equals(NAME)) {
            object.setName(attribute.getValue());
        }
    }

    @Override
    public void onCallbackInteger(Enigma object, Attribute<Integer> attribute) {
        if (attribute.getName().equals(IDENIGMA)) {
            object.setItemId(attribute.getValue());
        }
    }

    @Override
    public void onCallbackDouble(Enigma object, Attribute<Double> attribute) {
        if (attribute.getName().equals(PRICE)) {
            object.setPrice(attribute.getValue());
        }
    }

    @Override
    public void onCallbackMaterial(Enigma object, Attribute<Material> attribute) {}

    @Override
    public void onCallbackBoolean(Enigma object, Attribute<Boolean> attribute) {}

    @Override
    public void onCallbackTheme(Enigma object, Attribute<Theme> attribute) {}
}
