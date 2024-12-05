package managers;

import DAO.implementations.EnigmaDAOImpl;
import DAO.implementations.GiftDAOImpl;
import DAO.implementations.UserDAOImpl;
import classes.User;
import classes.item.implementations.Enigma;
import classes.item.implementations.Gift;
import exceptions.IncorrectMenuOptionException;
import utils.Entry;
import utils.MenuUserOptions;

import java.util.List;

public class UserManager {

    private static UserManager instance;
    private final UserDAOImpl daoUser;
    private final GiftDAOImpl daoGift;
    private final EnigmaDAOImpl daoEnigma;

    public UserManager(){
        this.daoUser = new UserDAOImpl();
        this.daoGift = new GiftDAOImpl();
        this.daoEnigma = new EnigmaDAOImpl();
    }

    public static UserManager getInstance(){
        if (instance == null) instance = new UserManager();
        return instance;
    }

    public void start() {
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
                    createUser();
                    break;
                case 2:
                    subscribeUser();
                    break;
                case 3:
                    printCertificates();
                    break;
                case 4:
                    showGifts();
                    break;
                case 0:
                    close = true;
                    break;
                default: break;
            }
        } while (!close);
    }

    private int menu() throws IncorrectMenuOptionException {
        System.out.println("\nMenu User:");
        for (int i = 1; i <= MenuUserOptions.options.length; i++) {
            System.out.println( i + ". " + MenuUserOptions.options[i-1]);
        }
        System.out.println("0. " + MenuUserOptions.close);

        int menuOption = Entry.readInt("Select a menu option between 0 and " +
                MenuUserOptions.options.length + ".");
        if (menuOption < 0 || menuOption > MenuUserOptions.options.length)
            throw new IncorrectMenuOptionException("Menu option should be between 0 and " +
                    MenuUserOptions.options.length + ".");
        else return menuOption;
    }

    public void createUser(){
        String name = Entry.readString("Please type user's name: ");
        String email = Entry.readString("Please type user's email: ");
        User user = new User(name, email);
        getInstance().daoUser.add(user);
    }

    public void subscribeUser(){
        List<User> users = daoUser.getData();
        User user = selectUser();
        if (user == null){
            System.out.println("No user found");
        }else{
            Boolean isSubscriber = Entry.readBoolean("Subscribe user to newsletter? Yes > Y, No > N");
            user.setIsSuscriber(isSubscriber);
            getInstance().daoUser.updateUser(user);
        }
    }

    public void printCertificates(){
        User currentUser = selectUser();
        if (currentUser == null){
            System.out.println("No user found");
            return;
        }
        final String userName = currentUser.getName();

        List<Enigma> enigmas;

        enigmas = getInstance().daoEnigma.getAllEnigmasByUser(currentUser);
        if (enigmas.isEmpty()) System.out.println("Sorry, the player " + userName +
                                " has not solved any enigma yet.");
        else{
            enigmas.forEach(e -> System.out.println("This escape room certifies that player " + userName +
                    " solved successfully the enigma '" + e.getName() + "'."));
        }
    }

    public void showGifts(){
        User user = selectUser();
        if (user == null){
            System.out.println("No user found");
            return;
        }
        final String userName = user.getName();
        List<Gift> gifts;
            gifts = getInstance().daoGift.getAllGiftsByUser(user.getId());
            if (gifts.isEmpty()) System.out.println("Sorry, the player " + userName +
                    " has not won any reward yet");
            else{
                System.out.println("The player " + userName + " has won the following rewards:");
                gifts.forEach(System.out::println);
            }
    }

    public User checkUserInList(Integer id, List<User> users){
        return users.stream().filter(user -> user.getId().equals(id))
                            .findFirst().orElse(null);
    }

    public User selectUser(){
        List<User> users = getInstance().daoUser.getData();
        if (users.isEmpty()) return null;

        System.out.println("--- USER LIST ---");
        Integer id;
        User currentUser = null;
        users.forEach(System.out::println);
        do{
            id = Entry.readInt("Select user id >> ");
            currentUser = checkUserInList(id, users);
        }while (currentUser == null);
        return currentUser;
    }
}


