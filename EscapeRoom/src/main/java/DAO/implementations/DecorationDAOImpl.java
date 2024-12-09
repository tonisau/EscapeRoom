package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.DecorationDAO;
import classes.enums.Material;
import classes.item.ItemFactory;
import classes.item.implementations.Decoration;
import classes.item.implementations.ItemFactoryImpl;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import DAO.Query;
import connections.callback.ParsingCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DecorationDAOImpl implements DecorationDAO, ParsingCallback<Decoration> {

    DbConnectionImpl dbConnection = DbConnectionImpl.getInstance();
    Parser<Decoration> parser = new Parser<>(this);
    ItemFactory itemFactory = new ItemFactoryImpl();

    private static final String IDDECORATION = "iddecoration";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String MATERIAL = "material";
    private static final String QUANTITY = "quantity";

    @Override
    public Boolean addDecoration(Decoration decoration, Integer roomId) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(decoration.getName(), String.class));
        attributeList.add(new Attribute<>(decoration.getMaterial().name(), String.class));
        attributeList.add(new Attribute<>(decoration.getPrice(), Double.class));
        attributeList.add(new Attribute<>(decoration.getQuantity(), Integer.class));
        attributeList.add(new Attribute<>(roomId, Integer.class));
        return dbConnection.create(Query.CREATEDECORATION, attributeList);
    }

    @Override
    public Decoration getDecorationById(Integer itemId) {
        List<Attribute> queryAttributeList = List.of(new Attribute<>(itemId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDDECORATION, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class),
                new Attribute<>(MATERIAL, null, String.class),
                new Attribute<>(QUANTITY, null, Integer.class));

        return this.getDecorations(Query.GETDECORATIONBYID, queryAttributeList, outputAttributes).getFirst();
    }

    @Override
    public List<Decoration> getAllDecorationsByRoom(Integer roomId) {
        List<Attribute> queryAttributeList = List.of(new Attribute<>(roomId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDDECORATION, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class),
                new Attribute<>(MATERIAL, null, String.class),
                new Attribute<>(QUANTITY, null, Integer.class));

        return this.getDecorations(Query.GETDECORATIONEBYROOM, queryAttributeList, outputAttributes);
    }

    @Override
    public List<Decoration> getData() {
        List<Attribute> queryAttributeList = List.of();
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDDECORATION, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class),
                new Attribute<>(MATERIAL, null, String.class),
                new Attribute<>(QUANTITY, null, Integer.class));

        return this.getDecorations(Query.GETALLDECORATIONS, queryAttributeList, outputAttributes);
    }

    private List<Decoration> getDecorations(String query, List<Attribute> queryAttributes, List<Attribute> outputAttributes) {
        List<Decoration> decorations = new ArrayList<>();

        List<HashSet<Attribute>> decorationList = dbConnection.query(query, queryAttributes, outputAttributes);


        if (decorationList.isEmpty()) return List.of();

        for (HashSet<Attribute> attributeValues: decorationList) {
            Decoration decoration = itemFactory.createDecoration();
            parser.parseObject(decoration, attributeValues);
            decorations.add(decoration);
        }

        return decorations;
    }

    @Override
    public void delete(Integer id) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(id, Integer.class));
        dbConnection.delete(Query.DELETEDECORATION, queryAttributeList);
    }

    @Override
    public void onCallbackString(Decoration object, Attribute<String> attribute) {
        switch (attribute.getName()) {
            case NAME -> object.setName(attribute.getValue());
            case MATERIAL -> object.setMaterial(Material.valueOf(attribute.getValue()));
        }
    }

    @Override
    public void onCallbackInteger(Decoration object, Attribute<Integer> attribute) {
        switch (attribute.getName()) {
            case IDDECORATION -> object.setItemId(attribute.getValue());
            case QUANTITY -> object.setQuantity(attribute.getValue());
        }
    }

    @Override
    public void onCallbackDouble(Decoration object, Attribute<Double> attribute) {
        if (attribute.getName().equals(PRICE)) {
            object.setPrice(attribute.getValue());
        }
    }

    @Override
    public void onCallbackBoolean(Decoration object, Attribute<Boolean> attribute) {}
}
