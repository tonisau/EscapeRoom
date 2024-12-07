package managers;

import DAO.implementations.RoomDAOImpl;
import DAO.implementations.TicketDAOImpl;
import DAO.implementations.UserDAOImpl;
import classes.Room;
import classes.Ticket;
import classes.User;
import utils.Entry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketManager {
    private final static double PLAYER_PRICE = 20;
    private static TicketManager instance;
    private final TicketDAOImpl daoTicket;
    private final RoomDAOImpl daoRoom;
    private final UserDAOImpl daoUser;

    public TicketManager () {
        this.daoTicket = new TicketDAOImpl();
        this.daoUser = new UserDAOImpl();
        this.daoRoom = new RoomDAOImpl();
    }

    public void generateTicket(){
        Ticket ticket = createTicket();
        this.daoTicket.addTicket(ticket);
        System.out.println("Printing ticket...");
        System.out.println(ticket);
        System.out.println("Starting the game...");
        GameManager gameManager = new GameManager(ticket.getUsers(), ticket.getRoomId());
        gameManager.playGame();
    }

    public Ticket createTicket(){
        Ticket ticket = null;
        List<Room> rooms = getRooms();
        if (rooms.isEmpty()) {
            System.out.println("Cannot create ticket");
            return null;
        }
        Integer roomId = selectRoom(rooms);
        if (roomId != -1){
            List<User> players = getplayers();
            if (!players.isEmpty()){
                double price = PLAYER_PRICE * players.size();
                LocalDateTime saleDate = LocalDateTime.now();
                ticket = new Ticket(roomId, saleDate, price, players);
            }
        }
        return ticket;
    }

    public List<User> getplayers(){
        List<User> players = new ArrayList<>();
        List<User> users = this.daoUser.getData();
        Integer id;
        if (users.isEmpty()){
            System.out.println("No player found in this escape room. A ticket cannot be issued.");
        }else{
            do{
                System.out.println("--- PLAYER LIST ---");
                users.forEach(System.out::println);
                id = Entry.readInt("Select player id from list or type 0 to finish >> ");
                if(id.equals(0)) break;

                User player = checkUser(id, users);
                if(player == null){
                    System.out.println("Incorrect selection, please choose a user id from the list.");
                }else if (checkUserNotSelected(id, players)){
                    players.add(player);
                }
                else{
                    System.out.println("User already selected as player");
                }
            } while (id !=0);
        }
        return players;
    }

    public User checkUser(int id, List<User> users){
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    public boolean checkUserNotSelected(int id, List<User> players){
        return players.stream().filter(player -> player.getId() == id).toList().isEmpty();
    }

    public void calcTotalIncome(){
        LocalDateTime dateFrom;
        LocalDateTime dateTo;
        System.out.println("To calculate total income of a period, enter both start and end dates:");
        do{
            dateFrom = Entry.readLocalDateTime("Type start date ('yyyy-MM-dd'): ");
        }while (!checkDateFrom(dateFrom));

        do{
            dateTo = Entry.readLocalDateTime("Type end date ('yyyy-MM-dd'): ");
        }while (!checkDateTo(dateTo, dateFrom));

        double income = this.daoTicket.getIncomeBetweenDates(dateFrom, dateTo);
        System.out.println("The total sale amount of the escape room between " +
                        dateFrom + " and " + dateTo + " is : " +
                        String.format("%.2f", income) + "€.");
    }

    public boolean checkDateFrom(LocalDateTime date){
        return date.isBefore(LocalDateTime.now());
    }

    public boolean checkDateTo(LocalDateTime dateTo, LocalDateTime dateFrom){
        boolean isValid = dateTo.isAfter(dateFrom);
        if (!isValid) System.out.println("The end date must be later than the initial date (all dates are by default at 00:00 hour).");
        return isValid;
    }

    public Integer selectRoom(List<Room> rooms){
        System.out.println("--- ROOM LIST ---");
        Integer id;
        Room currentRoom;
        rooms.forEach(System.out::println);

        id = Entry.readInt("Select room id >> ", rooms.stream().map(Room::getIdRoom).toList());
        currentRoom = rooms.stream().filter(room -> room.getIdRoom().equals(id))
                .findFirst().orElse(null);
        return currentRoom == null ? -1 :currentRoom.getIdRoom();
    }

    public List<Room> getRooms(){
        List<Room> rooms = this.daoRoom.getData();
        if(rooms.isEmpty()) System.out.println("No room found in this escape room.");
        return rooms;
    }
}
