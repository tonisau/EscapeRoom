package managers;

import DAO.implementations.*;
import DAO.interfaces.*;
import classes.Room;
import classes.enums.Material;
import classes.enums.Theme;
import classes.item.Item;
import classes.item.ItemFactory;
import classes.item.implementations.*;
import connections.DbConnectionImpl;
import exceptions.IncorrectMenuOptionException;
import subscription.Observable;
import utils.Entry;
import utils.MenuDeleteInventoryOptions;
import utils.RoomHelperImpl;
import utils.RoomHelper;

import java.util.List;
import java.util.stream.IntStream;

public class InventoryManager {

    private static InventoryManager instance;

    private RoomHelper roomHelper;

    private RoomDAO roomDAO;
    private EnigmaDAO enigmaDAO;
    private ClueDAO clueDAO;
    private DecorationDAO decorationDAO;
    private GiftDAO giftDAO;
    private  UserDAO userDAO;

    private ItemFactory itemFactory;
    private Observable observable;

    private InventoryManager(){
        this.roomHelper = new RoomHelperImpl();

        this.roomDAO = new RoomDAOImpl(DbConnectionImpl.getInstance());
        enigmaDAO = new EnigmaDAOImpl();
        clueDAO = new ClueDAOImpl();
        decorationDAO = new DecorationDAOImpl(DbConnectionImpl.getInstance());
        giftDAO = new GiftDAOImpl();
        userDAO = new UserDAOImpl();

        itemFactory = new ItemFactoryImpl();
    }

    public static InventoryManager getInstance(){
        if (instance == null) instance = new InventoryManager();
        return instance;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

    public void setRoomHelper(RoomHelper roomHelper) {
        this.roomHelper = roomHelper;
    }

    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public void addRoomToEscapeRoom(Integer escapeRoomId) {
        Room room = roomHelper.createRoom();
        Boolean created = roomDAO.addRoom(room, escapeRoomId);
        if (created) observable.notifySubscribers("New room created " + room.getName());
    }

    public void addNewEnigma() {
        List<Room> rooms = getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("You need to add a room first");
            return;
        }
        System.out.println("--- ROOM LIST ---");
        rooms.forEach(System.out::println);
        addEnigmaToRoom(rooms.stream().map(Room::getIdRoom).toList());
    }

    public void addNewDecoration() {
        List<Room> rooms = getAllRooms();
        if (rooms.isEmpty()) {
           System.out.println("You need to add a room first");
           return;
        }
        System.out.println("--- ROOM LIST ---");
        rooms.forEach(System.out::println);
        addDecorationToRoom(rooms.stream().map(Room::getIdRoom).toList());
    }

    public void createGift() {
        String name = Entry.readString("Give a name for the gift");
        giftDAO.addGift(itemFactory.createGift(name, 0.0));
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
        System.out.println("--- ROOM LIST ---");
        rooms.forEach(System.out::println);
        List<Enigma> enigmas = getEnigmasForRoom(rooms.stream().map(Room::getIdRoom).toList());
        if (enigmas.isEmpty()) {
            System.out.println("You need to add an enigma first");
            return;
        }

        System.out.println("--- ENIGMA LIST ---");
        enigmas.forEach(System.out::println);
        addClueForEnigma(enigmas.stream().map(Enigma::getItemId).toList());
    }

    public void showInventory(Integer escapeRoomId) {
        List<Room> rooms = roomDAO.getAllRoomsByEscapeRoom(escapeRoomId);
        for (Room room: rooms) {
            System.out.println(room);
            decorationDAO.getAllDecorationsByRoom(room.getIdRoom()).forEach(deco -> System.out.println("  " + deco));

            List<Enigma> enigmas = enigmaDAO.getAllEnigmasByRoom(room.getIdRoom());
            for (Enigma enigma: enigmas) {
                System.out.println("  " + enigma);
                clueDAO.getAllCluesByEnigma(enigma.getItemId()).forEach(clue -> System.out.println("    " + clue));
            }

            System.out.println();
        }
    }

    public String showTotalInventoryValue(Integer escapeRoomId) {
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
        return ("Total inventory value:" + totalValue);
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
                    List<Room> rooms = getAllRooms();
                    if(!rooms.isEmpty()){
                        rooms.forEach(System.out::println);
                        deleteRoom(rooms);
                    }else {
                        System.out.println("No rooms available!");
                    }
                    break;
                case 2:
                    List<Enigma> enigmas = getAllEnigmas();
                    if(!enigmas.isEmpty()){
                        enigmas.forEach(System.out::println);
                        deleteEnigma(enigmas);
                    }else {
                        System.out.println("No enigmas available!");
                    }
                    break;
                case 3:
                    List<Clue> clues = getAllClues();
                    if(!clues.isEmpty()){
                        clues.forEach(System.out::println);
                        deleteClue(clues);
                    }else {
                        System.out.println("No clues available!");
                    }
                    break;
                case 4:
                    List<Decoration> decorations = getAllDecoration();
                    if (!decorations.isEmpty()){
                        decorations.forEach(System.out::println);
                        deleteDecoration(decorations);
                    }else{
                        System.out.println("No decoration available!");
                    }
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

        List<Integer> validOptions = IntStream.rangeClosed(0, MenuDeleteInventoryOptions.options.length).boxed().toList();
        int menuOption = Entry.readInt("Select a menu option between 0 and " + MenuDeleteInventoryOptions.options.length + ".", validOptions);
        if (menuOption < 0 || menuOption > MenuDeleteInventoryOptions.options.length) throw new IncorrectMenuOptionException("Menu option should be between 0 and " + MenuDeleteInventoryOptions.options.length + ".");
        else return menuOption;
    }

    private void deleteRoom(List<Room> rooms) {
        Integer roomId = Entry.readInt("Enter a room id",
                rooms.stream().map(Room::getIdRoom).toList());
        roomDAO.delete(roomId);
    }

    private List<Room> getAllRooms() {
        return roomDAO.getData();
    }

    private void addEnigmaToRoom(List<Integer> roomIds) {
        Integer roomId = Entry.readInt("Enter a room id", roomIds);
        String name = Entry.readString("Give a name for the enigma");
        Double price = Entry.readDouble("Enter a price for the enigma");
        Enigma enigma = itemFactory.createEnigma(name, price);
        Boolean created = enigmaDAO.addEnigma(enigma, roomId);
        if (created) observable.notifySubscribers("New enigma created '" + enigma.getName() + "' for room with id: " + roomId);
    }

    private List<Enigma> getEnigmasForRoom(List<Integer> roomIds) {
        Integer roomId = Entry.readInt("Enter a room id", roomIds);
        return enigmaDAO.getAllEnigmasByRoom(roomId);
    }

    private List<Enigma> getAllEnigmas() {
        return enigmaDAO.getData();
    }

    private void deleteEnigma(List<Enigma> enigmas) {
        Integer enigmaId = Entry.readInt("Enter an enigma id",
                enigmas.stream().map(Item::getItemId).toList());
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
        Integer quantity = Entry.readInt("How many decoration objects do you have?");
        Decoration decoration = itemFactory.createDecoration(name, price, material, quantity);
        Boolean created = decorationDAO.addDecoration(decoration, roomId);
        if (created) observable.notifySubscribers("New decoration object created '" + decoration.getName() + "' for room with id: " + roomId);
    }

    private List<Decoration> getAllDecoration() {
        return decorationDAO.getData();
    }

    private void deleteDecoration(List<Decoration> decorations) {
        Integer decorationId = Entry.readInt("Enter a decoration id",
                decorations.stream().map(Item::getItemId).toList());
        decorationDAO.delete(decorationId);
    }

    private void addClueForEnigma(List<Integer> enigmaIds) {
        Integer enigmaId = Entry.readInt("Enter an enigma id", enigmaIds);
        String name = Entry.readString("Give a name for the new clue");
        Double price = Entry.readDouble("Enter a price for the clue");
        Theme theme = Entry.readTheme("Give a Theme for the clue (detective/futurist/cowboys)");
        Clue clue = itemFactory.createClue(name, price, theme);
        Boolean created = clueDAO.addClue(clue, enigmaId);
        if (created) observable.notifySubscribers("New clue created '" + clue.getName() + "' for enigma with id: " + enigmaId);
    }

    private List<Clue> getAllClues() {
        return clueDAO.getData();
    }

    private void deleteClue(List<Clue> clues) {
        Integer clueId = Entry.readInt("Enter a clue id",
                clues.stream().map(Item::getItemId).toList());
        clueDAO.delete(clueId);
    }
}
