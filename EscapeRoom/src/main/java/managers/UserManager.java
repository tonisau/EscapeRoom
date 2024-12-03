package managers;

import DAO.implementations.GiftDAOImpl;
import DAO.interfaces.implementations.UserDAOImpl;
import classes.User;
import classes.item.implementations.Gift;
import exceptions.IncorrectMenuOptionException;
import utils.Entry;
import utils.MenuUserOptions;

import java.util.List;

public class UserManager {

    private static UserManager instance;
    private final UserDAOImpl daoUser;
    private final GiftDAOImpl daoGift;

    public UserManager(){
        this.daoUser = new UserDAOImpl();
        this.daoGift = new GiftDAOImpl();
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
        String email = Entry.readString("Please type user's email: ");
        User user = getInstance().daoUser.getUserByEmail(email);
        if (user == null){
            System.out.println("User not found with such email");
        }else{
            Boolean isSubscriber = Entry.readBoolean("Subscribe user to newsletter? Yes > Y, No > N");
            user.setIsSuscriber(isSubscriber);
            getInstance().daoUser.updateUser(user);
        }
    }

    public void printCertificates(){
        String email = Entry.readString("Please type user's email: ");
        User user = getInstance().daoUser.getUserByEmail(email);
        List<String> enigmas;
        if (user == null){
            System.out.println("User not found with such email");
        }else{
            enigmas = getInstance().daoUser.getCertificates(user);
            if (enigmas.isEmpty()) System.out.println("Sorry, the player " + user.getName() +
                                    " has not solved any enigma yet.");
            else{
                enigmas.forEach(e -> System.out.println("This escape room certifies that player " + user.getName() +
                        " solved successfully the enigma '" + e + "'."));
            }
        }
    }

    public void showGifts(){
        String email = Entry.readString("Please type user's email: ");
        User user = getInstance().daoUser.getUserByEmail(email);
        List<Gift> gifts;
        if (user == null){
            System.out.println("User not found with such email");
        }else{
            gifts = getInstance().daoGift.getGifts(user);
            if (gifts.isEmpty()) System.out.println("Sorry, the player " + user.getName() +
                    "has not won any reward yet");
            else{
                System.out.println("The player " + user.getName() + " has won the following rewards:");
                gifts.forEach(System.out::println);
            }
        }
    }


}


