package DAO.interfaces;

import classes.item.implementations.Clue;

import java.util.List;

public interface ClueDAO extends DAO<Clue> {
    void addClue(Clue clue, Integer enigmaId);
    List<Clue> getAllCluesByEnigma(Integer enigmaId);
    Clue getClueById(Integer itemId);
}
