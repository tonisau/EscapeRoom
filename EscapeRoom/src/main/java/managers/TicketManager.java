package managers;

import DAO.interfaces.implementations.DAORoomImpl;
import classes.Room;
import classes.Ticket;
import classes.User;
import utils.Entry;

import java.util.ArrayList;
import java.util.List;

public class TicketManager {
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

    public Ticket createTicket(){
        int roomId = selectRoom();
        List<Integer> players = getplayers();
        return null;
    }

    public int selectRoom(){
        int result = -1;
        int selId;
        List<Room> rooms = this.daoRoom.showData();
        if (rooms.isEmpty()){
            System.out.println("None available room found in this escape room. A ticket cannot be issued.")
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

    public List<Integer> getplayers(){
        List<Integer> players = new ArrayList<>();
        List<User> users = this.daoUser.showData();
        int id = -1;
        if (users.isEmpty()){
            System.out.println("No player found in this escape room. A ticket cannot be issued.");
        }else{
            do{
                System.out.println("--- PLAYER LIST ---");
                users.forEach(System.out::println);
                id = Entry.readInt("Select player id from list or type 0 to finish >> ");
                if(checkUserNotInList(id, users)){
                    System.out.println("Incorrect selection");
                }else if (checkUserNotSelected(id, players)){
                    players.add(id);
                }
                else{
                    System.out.println("User already selected as player");
                }
            } while (id !=0);
        }
        return players;
    }

    public boolean checkUserNotInList(int id, List<User> users){
        return users.stream().filter(user -> user.getId() == id).toList().isEmpty();
    }

    public boolean checkUserNotSelected(int id, List<Integer> players){
        return players.stream().filter(player -> player == id).toList().isEmpty();
    }

}
