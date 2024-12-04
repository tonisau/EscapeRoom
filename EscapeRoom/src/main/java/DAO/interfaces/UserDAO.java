package DAO.interfaces;

import classes.User;

import java.util.List;

public interface UserDAO extends DAO<User>{
    User getUser(Integer id);
    User getUserByEmail(String email);
    void updateUser(User user);
    List<String> getCertificates(User user);
}
