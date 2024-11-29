package managers;

import DAO.implementations.ItemDAOImpl;
import DAO.interfaces.ItemDAO;
import classes.item.implementations.Enigma;
import utils.Entry;

import java.util.List;

public class InventoryManager {

    public void addEnigmaToRoom() {

        int roomId = Entry.readInt("Enter a room id");
        String name = Entry.readString("Give a name for the enigma");
        double price = Entry.readDouble("Enter a price for the enigma");

        ItemDAO itemDAO = new ItemDAOImpl();
        itemDAO.addEnigma(new Enigma(name, price), roomId);
    }

    public List<Enigma> getEnigmasForRoom() {
        int roomId = Entry.readInt("Enter a room id");
        ItemDAO itemDAO = new ItemDAOImpl();
        List<Enigma> enigmas = itemDAO.getAllEnigmasByRoom(roomId);

        enigmas.forEach(System.out::println);
        return enigmas;
    }
}
