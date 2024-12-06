package DAO.interfaces;

import classes.item.implementations.Decoration;

import java.util.List;

public interface DecorationDAO extends DAO<Decoration> {
    Boolean addDecoration(Decoration decoration, Integer roomId);
    Decoration getDecorationById(Integer itemId);
    List<Decoration> getAllDecorationsByRoom(Integer roomId);
}
