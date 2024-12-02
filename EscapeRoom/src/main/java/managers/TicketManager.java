package managers;

import DAO.interfaces.implementations.DAORoomImpl;
import classes.Room;
import classes.Ticket;
import classes.User;
import utils.Entry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TicketManager {
    private final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private final static double PLAYER_PRICE = 20;
    private static TicketManager instance;
    private final TicketDAOImpl daoTicket;
    private final DAORoomImpl daoRoom;
    private final DAOUserImpl daoUser;

    private TicketManager () {
        this.daoTicket = new TicketDAOImpl();
        this.daoRoom = new DAORoomImpl();
    }

    public static TicketManager getInstance(){
        if (instance == null) instance = new TicketManager();
        return instance;
    }

    public void generateTicket(){
        Ticket ticket = createTicket();
        // save ticket in BDD

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
        List<Room> rooms = this.daoRoom.showData();
        if (rooms.isEmpty()){
            System.out.println("None available room found in this escape room.");
        }
        else{
            do{
                System.out.println("Choose a room among the following ones for the game: ");
                rooms.forEach(room -> System.out.println(room.getIdRoom() + ". " + room.getName()));
                selId = Entry.readInt("Your selection >> ");
            }while (checkRoomNotInList(selId, rooms));
            result = selId;
        }
        return result;
    }
    public boolean checkRoomNotInList(int id, List<Room> rooms){
        return rooms.stream().filter(room -> room.getIdRoom() == id).toList().isEmpty();
    }

    public List<User> getplayers(){
        List<User> players = new ArrayList<>();
        List<User> users = this.daoUser.showData();
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
        System.out.println("The total sale amount of the escape room is : " +
                            getTotalSales(this.daoTicket.showData()));
    }
    public double getTotalSales(List<Ticket> tickets){
        return tickets.stream().mapToDouble(Ticket::getPrice).sum();
    }

}
