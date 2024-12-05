package managers;

import DAO.implementations.*;
import DAO.interfaces.*;
import classes.EscapeRoom;
import classes.Room;
import classes.enums.Level;
import classes.enums.Material;
import classes.enums.Theme;
import classes.item.ItemFactory;
import classes.item.implementations.*;
import exceptions.IncorrectMenuOptionException;
import utils.Entry;
import utils.MenuDeleteInventoryOptions;

import java.util.List;

public class InventoryManager {

    private static InventoryManager instance;

    RoomDAO roomDAO = new RoomDAOImpl();
    EnigmaDAO enigmaDAO = new EnigmaDAOImpl();
    ClueDAO clueDAO;
    DecorationDAO decorationDAO;
    GiftDAO giftDAO;
    UserDAO userDAO;

    ItemFactory itemFactory;

    private InventoryManager(){
        roomDAO = new RoomDAOImpl();
        enigmaDAO = new EnigmaDAOImpl();
        clueDAO = new ClueDAOImpl();
        decorationDAO = new DecorationDAOImpl();
        giftDAO = new GiftDAOImpl();
        userDAO = new UserDAOImpl();

        itemFactory = new ItemFactoryImpl();
    }

    public static InventoryManager getInstance(){
        if (instance == null) instance = new InventoryManager();
        return instance;
    }

    public void addRoomToEscapeRoom(Integer escapeRoomId) {
        String name = Entry.readString("Give a name for the room");
        Double price = Entry.readDouble("Enter a price for the room");
        Level level = Entry.readLevel("Enter a level for the room (Low/Medium/High)");
        roomDAO.addRoom(new Room(name, price, level), escapeRoomId);
    }

    public void addNewEnigma() {
        List<Room> rooms = getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("You need to add a room first");
            return;
        }
        rooms.forEach(System.out::println);
        addEnigmaToRoom(rooms.stream().map(Room::getIdRoom).toList());
    }

    public void addNewDecoration() {
        List<Room> rooms = getAllRooms();
        if (rooms.isEmpty()) {
           System.out.println("You need to add a room first");
           return;
        }
        rooms.forEach(System.out::println);
        addDecorationToRoom(rooms.stream().map(Room::getIdRoom).toList());
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

    public void addNewClue() {
        List<Room> rooms = getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("You need to add a room first");
            return;
        }
        rooms.forEach(System.out::println);
        List<Enigma> enigmas = getEnigmasForRoom(rooms.stream().map(Room::getIdRoom).toList());
        if (enigmas.isEmpty()) {
            System.out.println("You need to add an enigma first");
            return;
        }

        enigmas.forEach(System.out::println);
        addClueForEnigma(enigmas.stream().map(Enigma::getItemId).toList());
    }

    public void showInventory(Integer escapeRoomId) {
        List<Room> rooms = roomDAO.getAllRoomsByEscapeRoom(escapeRoomId);
        for (Room room: rooms) {
            System.out.println(room);
            decorationDAO.getAllDecorationsByRoom(room.getIdRoom()).forEach(System.out::println);

            List<Enigma> enigmas = enigmaDAO.getAllEnigmasByRoom(room.getIdRoom());
            for (Enigma enigma: enigmas) {
                System.out.println(enigma);
                clueDAO.getAllCluesByEnigma(enigma.getItemId()).forEach(System.out::println);
            }

            System.out.println();
        }
    }

    public void showTotalInventoryValue(Integer escapeRoomId) {
        Double totalValue = 0.0;
        List<Room> rooms = roomDAO.getAllRoomsByEscapeRoom(escapeRoomId);
        for (Room room: rooms) {
            totalValue += room.getPrice();
            Double totalDeco = decorationDAO.getAllDecorationsByRoom(room.getIdRoom())
                    .stream()
                    .map(deco -> deco.getPrice()*deco.getQuantity())
                    .reduce(0.0, Double::sum);
            totalValue += totalDeco;

            List<Enigma> enigmas = enigmaDAO.getAllEnigmasByRoom(room.getIdRoom());
            for (Enigma enigma: enigmas) {
                totalValue += enigma.getPrice();
                Double totalClue = clueDAO.getAllCluesByEnigma(enigma.getItemId())
                        .stream()
                        .map(Clue::getPrice)
                        .reduce(0.0, Double::sum);
                totalValue += totalClue;
            }
        }
        System.out.println("Total inventory value:" + totalValue);
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

    private void deleteRoom() {
        Integer roomId = Entry.readInt("Enter a room id");
        roomDAO.delete(roomId);
    }

    private List<Room> getAllRooms() {
        return roomDAO.getData();
    }

    private void addEnigmaToRoom(List<Integer> roomIds) {
        Integer roomId = Entry.readInt("Enter a room id", roomIds);
        String name = Entry.readString("Give a name for the enigma");
        Double price = Entry.readDouble("Enter a price for the enigma");
        enigmaDAO.addEnigma(itemFactory.createEnigma(name, price), roomId);
    }

    private List<Enigma> getEnigmasForRoom(List<Integer> roomIds) {
        Integer roomId = Entry.readInt("Enter a room id", roomIds);
        return enigmaDAO.getAllEnigmasByRoom(roomId);
    }

    private List<Enigma> getAllEnigmas() {
        return enigmaDAO.getData();
    }

    private void deleteEnigma() {
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

    private void addDecorationToRoom(List<Integer> roomIds) {
        Integer roomId = Entry.readInt("Enter a room id", roomIds);
        String name = Entry.readString("Give a name for the new decoration object");
        Double price = Entry.readDouble("Enter a price for the decoration");
        Material material = Entry.readMaterial("Enter a material for the decoration (wood/plastic/paper/glass/metal)");
        Integer quantity = Entry.readInt("How may decoration objects do you have?");
        decorationDAO.addDecoration(itemFactory.createDecoration(name, price, material, quantity), roomId);
    }

    private List<Decoration> getAllDecoration() {
        return decorationDAO.getData();
    }

    private void deleteDecoration() {
        Integer decorationId = Entry.readInt("Enter a decoration id");
        decorationDAO.delete(decorationId);
    }

    private void addClueForEnigma(List<Integer> enigmaIds) {
        Integer enigmaId = Entry.readInt("Enter an enigma id", enigmaIds);
        String name = Entry.readString("Give a name for the new clue");
        Double price = Entry.readDouble("Enter a price for the clue");
        Theme theme = Entry.readTheme("Give a Theme for the clue (detective/futurist/cowboys)");
        clueDAO.addClue(itemFactory.createClue(name, price, theme), enigmaId);
    }

    private List<Clue> getAllClues() {
        return clueDAO.getData();
    }

    private void deleteClue() {
        Integer clueId = Entry.readInt("Enter a clue id");
        clueDAO.delete(clueId);
    }
}
