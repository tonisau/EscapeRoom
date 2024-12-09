package DAO.interfaces;

import classes.User;
import classes.item.implementations.Enigma;

import java.util.List;

public interface EnigmaDAO extends DAO<Enigma> {
    Boolean addEnigma(Enigma enigma, Integer roomId);
    void addEnigmaToUser(Integer userId, Integer enigmaId);
    List<Enigma> getAllEnigmasByRoom(Integer roomId);
    Enigma getEnigmaById(Integer itemId);
    List<Enigma> getAllEnigmasByUser(User user);
}
