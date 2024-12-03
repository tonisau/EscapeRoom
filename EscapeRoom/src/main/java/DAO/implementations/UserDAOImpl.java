package DAO.implementations;

import DAO.interfaces.UserDAO;
import classes.User;
import classes.enums.Material;
import connections.attribute.Attribute;
import connections.callback.ParsingCallback;

import java.util.List;

public class UserDAOImpl implements UserDAO, ParsingCallback<User> {
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

    }

    @Override
    public List<User> showData() {
        return List.of();
    }

    @Override
    public void onCallbackString(User object, Attribute<String> attribute) {

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
