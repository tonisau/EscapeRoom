package managers;

import classes.Room;
import classes.User;
import classes.item.implementations.Enigma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameManager {
    private List<User> players;
    private Room room;
    private ItemDAOImpl itemDAO;
    HashMap<User, Enigma> certificates;
    HashMap<User, Gift> gifts;


    public GameManager(List<User> players, Room room, ItemDAOImpl itemDAO){
        this.players = players;
        this.room = room;
        this.itemDAO = itemDAO;
    }

    public void playGame(){
        System.out.println("Trying to get out of the room!...");
        this.certificates = resolveEnigmas();
        this.gifts = grantGifts();
        System.out.println("Game has ended!...");
    }
    public HashMap<User, Enigma> resolveEnigmas(){
        // Replace following code with Get Enigma list of the room
        List<Enigma> enigmas = new ArrayList<>();
        enigmas.add(new Enigma("Enigma1", 12.00));
        enigmas.add(new Enigma("Enigma2", 14.50));

        HashMap<User, Enigma> certificates = new HashMap<>();
        enigmas.forEach(enigma -> {
            for (User player : players){
                if (solveEnigma()){
                    certificates.put(player, enigma);
                    break;
                }
            }
        });
        return certificates;
    }

    public boolean solveEnigma(){
        return new Random().nextBoolean();
    }

    public HashMap<User, Gift> grantGifts(){
        HashMap<User, Gift> grantedGifts = new HashMap<User, Gift>();
        List<Gift> availableGifts = new ArrayList<>();
        this.certificates.forEach(((user, enigma) -> {
            Gift gift = availableGifts.get(new Random().nextInt(availableGifts.size()));
            grantedGifts.put(user, gift);
        }));
        // write grantedGifts in BDD
        return grantedGifts;
    }
}
