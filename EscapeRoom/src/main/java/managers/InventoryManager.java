package managers;

import DAO.implementations.ItemDAOImpl;
import DAO.interfaces.ItemDAO;
import classes.enums.Theme;
import classes.item.implementations.Clue;
import classes.item.implementations.Enigma;
import utils.Entry;

import java.util.List;

public class InventoryManager {

    ItemDAO itemDAO = new ItemDAOImpl();

    public void addEnigmaToRoom() {

        int roomId = Entry.readInt("Enter a room id");
        String name = Entry.readString("Give a name for the enigma");
        double price = Entry.readDouble("Enter a price for the enigma");

        itemDAO.addEnigma(new Enigma(name, price), roomId);
    }

    public List<Enigma> getEnigmasForRoom() {
        int roomId = Entry.readInt("Enter a room id");
        List<Enigma> enigmas = itemDAO.getAllEnigmasByRoom(roomId);

        enigmas.forEach(System.out::println);
        return enigmas;
    }

    public void deleteEnigma() {
        int enigmaId = Entry.readInt("Enter an enigma id");
        itemDAO.deleteEnigma(enigmaId);
    }

    public void addClueForEnigma() {

        int enigmaId = Entry.readInt("Enter an enigma id");
        String name = Entry.readString("Give a name for the clue");
        double price = Entry.readDouble("Enter a price for the clue");
        Theme theme = Entry.readTheme("Give a Theme for the clue");

        itemDAO.addClue(new Clue(name, price, theme), enigmaId);
    }
}
