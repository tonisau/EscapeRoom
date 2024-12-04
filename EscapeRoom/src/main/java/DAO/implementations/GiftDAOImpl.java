package DAO.implementations;

import DAO.Parser;
import classes.User;
import classes.enums.Material;
import classes.item.implementations.Gift;
import classes.item.implementations.ItemFactoryImpl;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import connections.attribute.Query;
import connections.callback.ParsingCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class GiftDAOImpl implements ParsingCallback<Gift> {
    ItemFactoryImpl itemFactory;
    DbConnectionImpl dbConnection = DbConnectionImpl.getInstance();
    Parser<Gift> parser = new Parser<>(this);

    private static final String NAME = "name";
    private static final String IDGIFT = "idgift";

    public List<Gift> getGifts(User user) {
        List<Attribute> queryAttributeList = new ArrayList<>();
        queryAttributeList.add(new Attribute<>(user.getId(), Integer.class));
        List<Attribute> outputAttributes = Arrays.asList(
                new Attribute<>(IDGIFT, null, Integer.class),
                new Attribute<>(NAME, null, String.class));

        List<Gift> gifts = new ArrayList<>();

        List<HashSet<Attribute>> giftList = dbConnection.query(Query.GETGIFTS, queryAttributeList, outputAttributes);

        if(giftList.isEmpty()) return List.of();

        for (HashSet<Attribute> attributeValues: giftList) {
            Gift gift = itemFactory.createGift();
            parser.parseObject(gift, attributeValues);
            gifts.add(gift);
        }
        return gifts;
    }
    @Override
    public void onCallbackString(Gift object, Attribute<String> attribute) {
        if (attribute.getName().equals(NAME)) object.setName(attribute.getValue());
    }

    @Override
    public void onCallbackInt(Gift object, Attribute<Integer> attribute) {
        if (attribute.getName().equals(IDGIFT)) object.setItemId(attribute.getValue());
    }

    @Override
    public void onCallbackDouble(Gift object, Attribute<Double> attribute) {

    }

    @Override
    public void onCallbackMaterial(Gift object, Attribute<Material> attribute) {

    }

    @Override
    public void onCallbackBoolean(Gift object, Attribute<Boolean> attribute) {

    }
}
