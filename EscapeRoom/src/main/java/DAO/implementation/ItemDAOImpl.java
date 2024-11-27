package DAO.implementation;

import DAO.interfaces.ItemDAO;
import classes.item.implementations.Clue;
import classes.item.implementations.Decoration;
import classes.item.implementations.Enigma;

import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public void addEnigma(Enigma enigma, int roomId) {
        
    }

    @Override
    public Enigma getEnigmaById(int itemId) {
        return null;
    }

    @Override
    public List<Enigma> getAllEnigmasByRoom(int roomId) {
        return List.of();
    }

    @Override
    public void deleteEnigma(int itemId) {

    }

    @Override
    public void addClue(Clue clue, int enigmaId) {

    }

    @Override
    public Clue getClueById(int itemId) {
        return null;
    }

    @Override
    public List<Clue> getAllCluesByEnigma(int enigmaId) {
        return List.of();
    }

    @Override
    public void deleteClue(int itemId) {

    }

    @Override
    public void addDecoration(Decoration decoration, int roomId) {

    }

    @Override
    public Decoration getDecorationById(int itemId) {
        return null;
    }

    @Override
    public List<Decoration> getAllDecorationsByRoom(int roomId) {
        return List.of();
    }

    @Override
    public void deleteDecoration(int itemId) {

    }
}
