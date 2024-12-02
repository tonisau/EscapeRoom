package DAO.interfaces;

import classes.item.implementations.Clue;
import classes.item.implementations.Decoration;
import classes.item.implementations.Enigma;

import java.util.List;

public interface ItemDAO {
    void addEnigma(Enigma enigma, int roomId);
    Enigma getEnigmaById(int itemId);
    List<Enigma> getAllEnigmasByRoom(int roomId);
    void deleteEnigma(int itemId);

    void addClue(Clue clue, int enigmaId);
    Clue getClueById(int itemId);
    List<Clue> getAllCluesByEnigma(int enigmaId);
    void deleteClue(int itemId);

    void addDecoration(Decoration decoration, int roomId);
    Decoration getDecorationById(int itemId);
    List<Decoration> getAllDecorationsByRoom(int roomId);
    void deleteDecoration(int itemId);
}
