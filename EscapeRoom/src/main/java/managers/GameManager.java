package managers;

import DAO.implementations.EnigmaDAOImpl;
import DAO.implementations.GiftDAOImpl;
import classes.User;
import classes.item.implementations.Enigma;
import classes.item.implementations.Gift;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameManager {
    private final List<User> players;
    private final Integer roomId;
    private final EnigmaDAOImpl enigmaDAO;
    private final GiftDAOImpl giftDAO;
    HashMap<User, Enigma> certificates;
    HashMap<User, Gift> gifts;

    public GameManager(List<User> players, Integer roomId){
        this.players = players;
        this.roomId = roomId;
        this.enigmaDAO = new EnigmaDAOImpl();
        this.giftDAO = new GiftDAOImpl();
    }

    public void playGame(){
        System.out.println("Trying to get out of the room!...");
        this.certificates = resolveEnigmas();
        this.gifts = grantGifts();
        System.out.println("Game has ended, Congratulations!...");
        this.gifts.forEach((user, gift)
                -> System.out.println(user.getName() + " receives " + gift.getName()));
    }

    public HashMap<User, Enigma> resolveEnigmas(){

        List<Enigma> enigmas = this.enigmaDAO.getAllEnigmasByRoom(roomId);
        HashMap<User, Enigma> certificates = new HashMap<>();

        enigmas.forEach(enigma -> {
            for (User player : players){
                if (solveEnigma()){
                    certificates.put(player, enigma);
                    break;
                }
            }
        });

        if(!certificates.isEmpty()){
            certificates.forEach(((user, enigma)
                    -> enigmaDAO.addEnigmaToUser(user.getId(), enigma.getItemId())));
        }
        return certificates;
    }

    public boolean solveEnigma(){
        return new Random().nextBoolean();
    }

    public HashMap<User, Gift> grantGifts(){
        HashMap<User, Gift> grantedGifts = new HashMap<>();
        List<Gift> availableGifts = giftDAO.getData();
        if (availableGifts.isEmpty()) {
            return grantedGifts;
        }

        this.certificates.forEach(((user, enigma) -> {
            Gift gift = availableGifts.get(new Random().nextInt(availableGifts.size()));
            grantedGifts.put(user, gift);
        }));

        if(!grantedGifts.isEmpty()) grantedGifts.forEach((user, gift)
                -> giftDAO.assignGiftToUser( gift.getItemId(), user.getId()));
        return grantedGifts;
    }
}
