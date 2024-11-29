package DAO.interfaces;

import classes.User;

import java.util.List;

public interface UserDAO extends DAO<User>{
    User getUser(int id);
    void updateUser(User user);
    List<String> getCertificates(User user);
    List<String> getGifts(User user);
}
