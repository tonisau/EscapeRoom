package managers;

import exceptions.IncorrectMenuOptionException;
import utils.Entry;
import utils.MenuOptions;

public class EscapeRoomManager {

    InventoryManager inventoryManager = new InventoryManager();
    RoomManager roomManager = new RoomManager();
    TicketManager ticketManager = new TicketManager();
    UserManager userManager = new UserManager();

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
                    break;
                case 2:
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
}
