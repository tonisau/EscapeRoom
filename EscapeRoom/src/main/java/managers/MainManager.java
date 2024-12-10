package managers;

import classes.User;
import exceptions.IncorrectMenuOptionException;
import subscription.Observable;
import subscription.Subscriber;
import utils.Entry;
import utils.MenuOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainManager implements Observable {

    EscapeRoomManager escapeRoomManager;
    InventoryManager inventoryManager;
    TicketManager ticketManager;
    UserManager userManager;

    private List<Subscriber> subscriberList = new ArrayList<>();

    public MainManager() {
        escapeRoomManager = EscapeRoomManager.getInstance();
        inventoryManager = InventoryManager.getInstance();
        ticketManager = TicketManager.getInstance();
        userManager = UserManager.getInstance();

        userManager.setObservable(this);
        inventoryManager.setObservable(this);
    }

    public void start() {
        boolean close = false;
        int selectedMenuOption = -1;

        escapeRoomManager.createEscapeRoomIfNotPresent();
        Integer escapeRoomId = escapeRoomManager.getEscapeRoom().getIdEscaperoom();

        List<User> users = userManager.getAllUsers();
        if (!users.isEmpty()) {
            subscriberList = users
                    .stream().filter(User::isSuscriber)
                    .map(user -> (Subscriber) user)
                    .collect(Collectors.toList());
        }

        do {
            try {
                selectedMenuOption = menu();
            } catch (IncorrectMenuOptionException e) {
                System.out.println(e.getMessage());
            }

            switch (selectedMenuOption) {
                case 1:
                    inventoryManager.addRoomToEscapeRoom(escapeRoomId);
                    break;
                case 2:
                    inventoryManager.addNewEnigma();
                    break;
                case 3:
                    inventoryManager.addNewClue();
                    break;
                case 4:
                    inventoryManager.addNewDecoration();
                    break;
                case 5:
                    inventoryManager.showInventory(escapeRoomId);
                    break;
                case 6:
                    System.out.println(inventoryManager.showTotalInventoryValue(escapeRoomId));
                    break;
                case 7:
                    inventoryManager.deleteMenuStart();
                    break;
                case 8:
                    userManager.start();
                    break;
                case 9:
                    ticketManager.generateTicket();
                    break;
                case 10:
                    ticketManager.calcTotalIncome();
                    break;
                case 11:
                    inventoryManager.createGift();
                    break;
                case 0:
                    close = true;
                    break;
                default: break;
            }
        } while (!close);
    }

    public int menu() throws IncorrectMenuOptionException {

        System.out.println("\nMenu:");
        for (int i = 1; i <= MenuOptions.options.length; i++) {
            System.out.println( i + ". " + MenuOptions.options[i-1]);
        }
        System.out.println("0. " + MenuOptions.close);

        int menuOption = Entry.readInt("Select a menu option between 0 and " + MenuOptions.options.length + ".");
        if (menuOption < 0 || menuOption > MenuOptions.options.length) throw new IncorrectMenuOptionException("Menu option should be between 0 and " + MenuOptions.options.length + ".");
        else return menuOption;
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscriberList.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscriberList.remove(subscriber);
    }

    @Override
    public void notifySubscribers(String message) {
        if (subscriberList.isEmpty()) return;
        for (Subscriber subscriber: subscriberList) {
            subscriber.update(message);
        }
    }
}
