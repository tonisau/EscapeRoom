package DAO.interfaces;

import classes.User;

import java.util.List;

public interface UserDAO extends DAO<User>{
    Boolean add(User user);
    User getUser(Integer id);
    Boolean updateUser(User user);
    List<User> getUsersWithEnigma(Integer enigmaId);
    void deleteUsersWithEnigma(Integer enigmaId);
}
