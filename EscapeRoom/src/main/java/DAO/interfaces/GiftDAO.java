package DAO.interfaces;

import classes.item.implementations.Gift;

import java.util.List;

public interface GiftDAO extends DAO<Gift> {
    void addGift(Gift gift);
    List<Gift> getAllGiftsByUser(Integer userId);
    Gift getGiftById(Integer itemId);
    void assignGiftToUser(Integer giftId, Integer userId);
}
