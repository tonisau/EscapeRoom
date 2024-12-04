package DAO.implementations;

import DAO.Parser;
import classes.enums.Material;
import DAO.interfaces.GiftDAO;
import classes.enums.Theme;
import classes.item.ItemFactory;
import classes.item.implementations.Gift;
import classes.item.implementations.ItemFactoryImpl;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import DAO.Query;
import connections.callback.ParsingCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class GiftDAOImpl implements GiftDAO, ParsingCallback<Gift> {

    DbConnectionImpl dbConnection = DbConnectionImpl.getInstance();
    Parser<Gift> parser = new Parser<>(this);
    ItemFactory itemFactory = new ItemFactoryImpl();

    private static final String IDGIFT = "idgift";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    @Override
    public void addGift(Gift gift) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(gift.getName(), String.class));
        attributeList.add(new Attribute<>(gift.getPrice(), Double.class));
        dbConnection.create(Query.CREATEGIFT, attributeList);
    }

    @Override
    public List<Gift> getAllGiftsByUser(Integer userId) {
        List<Attribute> queryAttributeList = List.of(new Attribute<>(userId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDGIFT, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class));

        return this.getGifts(Query.GETGIFTBYUSER, queryAttributeList, outputAttributes);
    }

    @Override
    public Gift getGiftById(Integer itemId) {
        List<Attribute> queryAttributeList = List.of(new Attribute<>(itemId, Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDGIFT, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class));

        return this.getGifts(Query.GETGIFTBYID, queryAttributeList, outputAttributes).getFirst();
    }

    @Override
    public void assignGiftToUser(Integer giftId, Integer userId) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(giftId, Integer.class));
        attributeList.add(new Attribute<>(userId, Integer.class));
        dbConnection.create(Query.CREATEUSERHASGIFT, attributeList);
    }

    @Override
    public List<Gift> getData() {
        List<Attribute> queryAttributeList = List.of();
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDGIFT, null, Integer.class),
                new Attribute<>(NAME, null, String.class),
                new Attribute<>(PRICE, null, Double.class));

        return this.getGifts(Query.GETALLGIFTS, queryAttributeList, outputAttributes);
    }

    private List<Gift> getGifts(String query, List<Attribute> queryAttributes, List<Attribute> outputAttributes) {

        List<Gift> gifts = new ArrayList<>();

        List<HashSet<Attribute>> giftList = dbConnection.query(query, queryAttributes, outputAttributes);

        if (giftList.isEmpty()) return List.of();

        for (HashSet<Attribute> attributeValues: giftList) {
            Gift gift = itemFactory.createGift();
            parser.parseObject(gift, attributeValues);
            gifts.add(gift);
        }

        return gifts;
    }

    @Override
    public void delete(Integer id) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute(id, Integer.class));
        dbConnection.delete(Query.DELETEGIFT, queryAttributeList);
    }

    @Override
    public void onCallbackString(Gift object, Attribute<String> attribute) {
        if (attribute.getName().equals(NAME)) {
            object.setName(attribute.getValue());
        }
    }

    @Override
    public void onCallbackInteger(Gift object, Attribute<Integer> attribute) {
        if (attribute.getName().equals(IDGIFT)) {
            object.setItemId(attribute.getValue());
        }
    }

    @Override
    public void onCallbackDouble(Gift object, Attribute<Double> attribute) {
        if (attribute.getName().equals(PRICE)) {
            object.setPrice(attribute.getValue());
        }
    }

    @Override
    public void onCallbackMaterial(Gift object, Attribute<Material> attribute) {}

    @Override
    public void onCallbackTheme(Gift object, Attribute<Theme> attribute) {}

    @Override
    public void onCallbackBoolean(Gift object, Attribute<Boolean> attribute) {}
}
