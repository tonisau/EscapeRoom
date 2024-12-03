package DAO.implementations;

import DAO.Parser;
import DAO.interfaces.UserDAO;
import classes.User;
import classes.enums.Material;
import classes.item.Item;
import connections.DbConnectionImpl;
import connections.attribute.Attribute;
import connections.attribute.Query;
import connections.callback.ParsingCallback;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO, ParsingCallback<User> {

    DbConnectionImpl dbConnection = DbConnectionImpl.getInstance();
    Parser<User> parser = new Parser<>(this);

    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String IS_SUBSCRIBER = "isSubscriber";
    private static final String IDUSER = "iduser";

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public void updateUser(User user) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(user.getName(), String.class));
        attributeList.add(new Attribute<>(user.getEmail(), String.class));
        attributeList.add(new Attribute<>(user.isSuscriber(), boolean.class));
        attributeList.add(new Attribute<>(user.getId(), Integer.class));
        dbConnection.create(Query.UPDATEUSER, attributeList);
    }

    @Override
    public List<String> getCertificates(User user) {
        return List.of();
    }

    @Override
    public List<String> getGifts(User user) {
        return List.of();
    }

    @Override
    public void add(User user) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute<>(user.getName(), String.class));
        attributeList.add(new Attribute<>(user.getEmail(), String.class));
        dbConnection.create(Query.CREATEUSER, attributeList);
    }

    @Override
    public List<User> showData() {
        return List.of();
    }

    @Override
    public void onCallbackString(User object, Attribute<String> attribute) {
        switch (attribute.getName()){
            case
        }

    }

    @Override
    public void onCallbackInt(User object, Attribute<Integer> attribute) {

    }

    @Override
    public void onCallbackDouble(User object, Attribute<Double> attribute) {

    }

    @Override
    public void onCallbackMaterial(User object, Attribute<Material> attribute) {

    }
}
