package managers;

import DAO.interfaces.implementations.UserDAOImpl;
import classes.User;
import exceptions.IncorrectMenuOptionException;
import utils.Entry;
import utils.MenuOptions;
import utils.MenuUserOptions;

public class UserManager {

    private static UserManager instance;
    private UserDAOImpl daoUser;

    public UserManager(){
        this.daoUser = new UserDAOImpl();
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
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
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
            System.out.println( i + ". " + MenuOptions.options[i-1]);
        }
        System.out.println("0. " + MenuUserOptions.close);

        int menuOption = Entry.readInt("Select a menu option between 0 and " +
                MenuUserOptions.options.length + ".");
        if (menuOption < 0 || menuOption > MenuUserOptions.options.length)
            throw new IncorrectMenuOptionException("Menu option should be between 0 and " +
                    MenuOptions.options.length + ".");
        else return menuOption;
    }

    public void createUser(){
        String name = Entry.readString("Entre el nombre del usuario: ");
        String email = Entry.readString("Entre el email del usuario: ");
        User user = new User(name, email);
        getInstance().daoUser.add(user);
    }

    public void subscribeUser(){
        String email = Entry.readString("Entre el email del usuario: ");
        User user = getInstance().daoUser.getUserByEmail(email);
        if (user == null){
            System.out.println("User not found with such email");
        }else{
            boolean isSubscriber = Entry.readBoolean("Subscribe user to newsletter? Yes > Y, No > N");
            user.setIsSuscriber(isSubscriber);
            getInstance().daoUser.updateUser(user);
        }
    }

    public void showCertifications(){

    }

    public void showGifts(){

    }


}


