package DAO.interfaces;

import classes.item.implementations.Enigma;

import java.util.List;

public interface EnigmaDAO extends DAO<Enigma> {
    void addEnigma(Enigma enigma, Integer roomId);
    List<Enigma> getAllEnigmasByRoom(Integer roomId);
    Enigma getEnigmaById(Integer itemId);
}
