package managers;

import DAO.implementations.EscapeRoomDAOImpl;
import DAO.implementations.TicketDAOImpl;
import DAO.implementations.UserDAOImpl;
import DAO.interfaces.EscapeRoomDAO;
import DAO.interfaces.UserDAO;
//import DAO.interfaces.implementations.DAORoomImpl;
import classes.EscapeRoom;
import classes.Room;
import classes.Ticket;
import classes.User;
import classes.enums.Level;
import utils.Entry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TicketManager {

    private static TicketManager instance;
    private final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private final static double PLAYER_PRICE = 20;
    private final TicketDAOImpl daoTicket;
    //private final DAORoomImpl daoRoom;
    private final UserDAOImpl daoUser;

    private TicketManager(){
        this.daoTicket = new TicketDAOImpl();
        this.daoUser = new UserDAOImpl();
        //this.daoRoom = new DAORoomImpl();
    }

    public static TicketManager getInstance(){
        if (instance == null) instance = new TicketManager();
        return instance;
    }

    public void generateTicket(){
        Ticket ticket = createTicket();

        // save ticket in BDD
        this.daoTicket.addTicket(ticket);

        System.out.println("Printing ticket...");
        System.out.println(ticket.toString());
    }

    public Ticket createTicket(){
        Ticket ticket = null;
        int roomId = selectRoom();
        if (roomId != -1){
            List<User> players = getplayers();
            if (!players.isEmpty()){
                double price = PLAYER_PRICE * countPlayers(players);
                LocalDateTime saleDate = LocalDateTime.now();
                ticket = new Ticket(roomId, saleDate, price, players);
            }
        }
        return ticket;
    }

    public int selectRoom(){
        int result = -1;
        int selId;
        //Uncomment when daoRoom is ready -->
        //List<Room> rooms = this.daoRoom.showData();
        Room room = new Room("room1", 100.0, Level.LOW);

        /*if (rooms.isEmpty()){
            System.out.println("None available room found in this escape room.");
        }
        else{
            do{
                System.out.println("Choose a room among the following ones for the game: ");
                rooms.forEach(room -> System.out.println(room.getIdRoom() + ". " + room.getName()));
                selId = Entry.readInt("Your selection >> ");
            }while (checkRoomNotInList(selId, rooms));
            result = selId;
        }*/
//        return result;
        return room.getIdRoom();
    }
    public boolean checkRoomNotInList(int id, List<Room> rooms){
        return rooms.stream().filter(room -> room.getIdRoom() == id).toList().isEmpty();
    }

    public List<User> getplayers(){
        List<User> players = new ArrayList<>();
        List<User> users = this.daoUser.getData();
        int id = -1;
        if (users.isEmpty()){
            System.out.println("No player found in this escape room. A ticket cannot be issued.");
        }else{
            do{
                System.out.println("--- PLAYER LIST ---");
                users.forEach(System.out::println);
                id = Entry.readInt("Select player id from list or type 0 to finish >> ");
                User player = checkUser(id, users);
                if(player == null){
                    System.out.println("Incorrect selection");
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

    public int countPlayers(List<User> players){
        return players.size();
    }

    public void calcTotalIncome(){
        LocalDateTime dateFrom;
        LocalDateTime dateTo;
        System.out.println("To calculate total income of a period, enter both start and end dates:");
        do{
            dateFrom = Entry.readLocalDateTime("Type start date: ");
        }while (!checkDateFrom(dateFrom));

        do{
            dateTo = Entry.readLocalDateTime("Type end date: ");
        }while (!checkDateTo(dateTo, dateFrom));
//        LocalDateTime dateFrom = LocalDateTime.parse("2024-01-01T00:00:00");
//        LocalDateTime dateTo = LocalDateTime.parse("2024-12-31T00:00:00");
        double income = this.daoTicket.getIncomeBetweenDates(dateFrom, dateTo);

        System.out.println("The total sale amount of the escape room is : " +
                        String.format("%.2f", income) + "€.");
    }

    public boolean checkDateFrom(LocalDateTime date){
        return date.isBefore(LocalDateTime.now());
    }

    public boolean checkDateTo(LocalDateTime dateTo, LocalDateTime dateFrom){
        boolean isValid = dateTo.isAfter(dateFrom)||dateTo.isEqual(dateFrom);
        if (!isValid) System.out.println("The end date must be later or equal to the initial date.");
        return isValid;
    }
}
