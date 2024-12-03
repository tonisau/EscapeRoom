package managers;

import DAO.implementations.ClueDAOImpl;
import DAO.implementations.DecorationDAOImpl;
import DAO.implementations.EnigmaDAOImpl;
import DAO.implementations.GiftDAOImpl;
import DAO.interfaces.ClueDAO;
import DAO.interfaces.DecorationDAO;
import DAO.interfaces.EnigmaDAO;
import DAO.interfaces.GiftDAO;
import classes.enums.Material;
import classes.enums.Theme;
import classes.item.ItemFactory;
import classes.item.implementations.*;
import utils.Entry;

import java.util.List;

public class InventoryManager {

    EnigmaDAO enigmaDAO = new EnigmaDAOImpl();
    ClueDAO clueDAO = new ClueDAOImpl();
    DecorationDAO decorationDAO = new DecorationDAOImpl();
    GiftDAO giftDAO = new GiftDAOImpl();

    ItemFactory itemFactory = new ItemFactoryImpl();

    public void addEnigmaToRoom() {
        Integer roomId = Entry.readInt("Enter a room id");
        String name = Entry.readString("Give a name for the enigma");
        Double price = Entry.readDouble("Enter a price for the enigma");
        enigmaDAO.addEnigma(itemFactory.createEnigma(name, price), roomId);
    }

    public void addClueForEnigma() {
        Integer enigmaId = Entry.readInt("Enter an enigma id");
        String name = Entry.readString("Give a name for the clue");
        Double price = Entry.readDouble("Enter a price for the clue");
        Theme theme = Entry.readTheme("Give a Theme for the clue"); // TODO: Give the possible values
        clueDAO.addClue(itemFactory.createClue(name, price, theme), enigmaId);
    }

    public void addDecorationToRoom() {
        Integer roomId = Entry.readInt("Enter an room id");
        String name = Entry.readString("Give a name for the decoration");
        Double price = Entry.readDouble("Enter a price for the decoration");
        Material material = Entry.readMaterial("Enter a material for the decoration"); // TODO: Give the possible values
        Integer quantity = Entry.readInt("How may decoration objects do you have?");
        decorationDAO.addDecoration(itemFactory.createDecoration(name, price, material, quantity), roomId);
    }

    public void createGift() {
        String name = Entry.readString("Give a name for the gift");
        Double price = Entry.readDouble("Enter a price for the gift");
        giftDAO.addGift(itemFactory.createGift(name, price));
    }

    public List<Enigma> getEnigmasForRoom() {
        Integer roomId = Entry.readInt("Enter a room id");
        return enigmaDAO.getAllEnigmasByRoom(roomId);
    }

    public List<Enigma> getAllEnigmas() {
        return enigmaDAO.showData();
    }

    public void deleteEnigma() {
        Integer enigmaId = Entry.readInt("Enter an enigma id");
        enigmaDAO.delete(enigmaId);
    }

    public List<Decoration> getDecorationForRoom() {
        Integer roomId = Entry.readInt("Enter a room id");
        return decorationDAO.getAllDecorationsByRoom(roomId);
    }

    public List<Decoration> getAllDecoration() {
        return decorationDAO.showData(); // TODO: Rename show to get
    }

    public void deleteDecoration() {
        Integer decorationId = Entry.readInt("Enter a decoration id");
        decorationDAO.delete(decorationId);
    }

    public List<Gift> getGiftsForUser() {
        Integer userId = Entry.readInt("Enter a user id");
        return giftDAO.getAllGiftsByUser(userId);
    }

    public List<Gift> getAllGifts() {
        return giftDAO.showData();
    }

    public void deleteGift() {
        Integer giftId = Entry.readInt("Enter a gift id");
        giftDAO.delete(giftId);
    }

    public List<Clue> getCluesForEnigma() {
        Integer enigmaId = Entry.readInt("Enter an enigma id");
        return clueDAO.getAllCluesByEnigma(enigmaId);
    }

    public List<Clue> getAllClues() {
        return clueDAO.showData();
    }

    public void deleteClue() {
        Integer clueId = Entry.readInt("Enter a clue id");
        clueDAO.delete(clueId);
    }


}
