package managers;

import DAO.implementations.*;
import DAO.interfaces.*;
import classes.Room;
import classes.User;
import classes.enums.Level;
import classes.enums.Material;
import classes.enums.Theme;
import classes.item.ItemFactory;
import classes.item.implementations.*;
import exceptions.IncorrectMenuOptionException;
import utils.Entry;
import utils.MenuDeleteInventoryOptions;
import utils.MenuOptions;

import java.util.List;

public class InventoryManager {

    RoomDAO roomDAO = new RoomDAOImpl();
    EnigmaDAO enigmaDAO = new EnigmaDAOImpl();
    ClueDAO clueDAO = new ClueDAOImpl();
    DecorationDAO decorationDAO = new DecorationDAOImpl();
    GiftDAO giftDAO = new GiftDAOImpl();
    UserDAO userDAO = new UserDAOImpl();

    ItemFactory itemFactory = new ItemFactoryImpl();

    public void addRoomToEscapeRoom(Integer escapeRoomId) {
        String name = Entry.readString("Give a name for the room");
        Double price = Entry.readDouble("Enter a price for the room");
        Level level = Entry.readLevel("Enter a level for the room (Low/Medium/High)");
        roomDAO.addRoom(new Room(name, price, level), escapeRoomId);
    }

    public void deleteRoom() {
        Integer roomId = Entry.readInt("Enter a room id");
        roomDAO.delete(roomId);
    }

    public List<Room> getAllRooms() {
        return roomDAO.getData();
    }

    public void addEnigmaToRoom() {
        Integer roomId = Entry.readInt("Enter a room id");
        String name = Entry.readString("Give a name for the enigma");
        Double price = Entry.readDouble("Enter a price for the enigma");
        enigmaDAO.addEnigma(itemFactory.createEnigma(name, price), roomId);
    }

    public List<Enigma> getEnigmasForRoom() {
        Integer roomId = Entry.readInt("Enter a room id");
        return enigmaDAO.getAllEnigmasByRoom(roomId);
    }

    public List<Enigma> getAllEnigmas() {
        return enigmaDAO.getData();
    }

    public void deleteEnigma() {
        Integer enigmaId = Entry.readInt("Enter an enigma id");
        List<Clue> clues = clueDAO.getAllCluesByEnigma(enigmaId);
        Boolean confirm = true;
        if (!clues.isEmpty()) {
            System.out.println("Deleting enigma " + enigmaId + " will also delete clues:");
            clues.forEach(System.out::println);
            confirm = Entry.readBoolean("Do you confirm you want to delete the enigma " + enigmaId + " (Y/N)?");
        }

        if (confirm) {
            clues.forEach(clue -> clueDAO.delete(clue.getItemId()));
            userDAO.deleteUsersWithEnigma(enigmaId);
            enigmaDAO.delete(enigmaId);
        }
    }

    public void addDecorationToRoom() {
        Integer roomId = Entry.readInt("Enter an room id");
        String name = Entry.readString("Give a name for the decoration");
        Double price = Entry.readDouble("Enter a price for the decoration");
        Material material = Entry.readMaterial("Enter a material for the decoration"); // TODO: Give the possible values
        Integer quantity = Entry.readInt("How may decoration objects do you have?");
        decorationDAO.addDecoration(itemFactory.createDecoration(name, price, material, quantity), roomId);
    }

    public List<Decoration> getDecorationForRoom() {
        Integer roomId = Entry.readInt("Enter a room id");
        return decorationDAO.getAllDecorationsByRoom(roomId);
    }

    public List<Decoration> getAllDecoration() {
        return decorationDAO.getData(); // TODO: Rename show to get
    }

    public void deleteDecoration() {
        Integer decorationId = Entry.readInt("Enter a decoration id");
        decorationDAO.delete(decorationId);
    }

    public void createGift() {
        String name = Entry.readString("Give a name for the gift");
        Double price = Entry.readDouble("Enter a price for the gift");
        giftDAO.addGift(itemFactory.createGift(name, price));
    }

    public List<Gift> getGiftsForUser() {
        Integer userId = Entry.readInt("Enter a user id");
        return giftDAO.getAllGiftsByUser(userId);
    }

    public List<Gift> getAllGifts() {
        return giftDAO.getData();
    }

    public void deleteGift() {
        Integer giftId = Entry.readInt("Enter a gift id");
        giftDAO.delete(giftId);
    }

    public void addClueForEnigma() {
        Integer enigmaId = Entry.readInt("Enter an enigma id");
        String name = Entry.readString("Give a name for the clue");
        Double price = Entry.readDouble("Enter a price for the clue");
        Theme theme = Entry.readTheme("Give a Theme for the clue (detective/futurist/cowboys)");
        clueDAO.addClue(itemFactory.createClue(name, price, theme), enigmaId);
    }

    public List<Clue> getCluesForEnigma() {
        Integer enigmaId = Entry.readInt("Enter an enigma id");
        return clueDAO.getAllCluesByEnigma(enigmaId);
    }

    public List<Clue> getAllClues() {
        return clueDAO.getData();
    }

    public void deleteClue() {
        Integer clueId = Entry.readInt("Enter a clue id");
        clueDAO.delete(clueId);
    }

    public void deleteMenuStart() {
        boolean close = false;
        int selectedMenuOption = -1;

        do {
            try {
                selectedMenuOption = menu();
            } catch (IncorrectMenuOptionException e) {
                System.out.println(e.getMessage());
            }
            switch (selectedMenuOption) {
                case 1:
                    getAllRooms().forEach(System.out::println);
                    deleteRoom();
                    break;
                case 2:
                    getAllEnigmas().forEach(System.out::println);
                    deleteEnigma();
                    break;
                case 3:
                    getAllClues().forEach(System.out::println);
                    deleteClue();
                    break;
                case 4:
                    getAllDecoration().forEach(System.out::println);
                    deleteDecoration();
                    break;
                case 5:
                    getAllGifts().forEach(System.out::println);
                    deleteGift();
                    break;
                case 0:
                    close = true;
                    break;
                default: break;
            }
        } while (!close);
    }

    public int menu() throws IncorrectMenuOptionException {
        System.out.println("\nWhat do you want to delete?");
        for (int i = 1; i <= MenuDeleteInventoryOptions.options.length; i++) {
            System.out.println( i + ". " + MenuDeleteInventoryOptions.options[i-1]);
        }
        System.out.println("0. " + MenuDeleteInventoryOptions.close);

        int menuOption = Entry.readInt("Select a menu option between 0 and " + MenuDeleteInventoryOptions.options.length + ".");
        if (menuOption < 0 || menuOption > MenuDeleteInventoryOptions.options.length) throw new IncorrectMenuOptionException("Menu option should be between 0 and " + MenuDeleteInventoryOptions.options.length + ".");
        else return menuOption;
    }
}
