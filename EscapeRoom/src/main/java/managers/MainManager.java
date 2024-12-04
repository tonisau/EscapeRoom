package managers;

import exceptions.IncorrectInputException;
import exceptions.IncorrectMenuOptionException;
import utils.Entry;
import utils.MenuOptions;

public class MainManager {

    EscapeRoomManager escapeRoomManager = new EscapeRoomManager();
    //RoomManager roomManager = new RoomManager();
    InventoryManager inventoryManager = new InventoryManager();
    TicketManager ticketManager = new TicketManager();
    UserManager userManager = new UserManager();

    public void start() {
        boolean close = false;
        int selectedMenuOption = -1;

        escapeRoomManager.createEscapeRoomIfNotPresent();

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
                    // TODO: Room manager show all rooms
                    inventoryManager.addEnigmaToRoom();
                    break;
                case 3:
                    inventoryManager.getEnigmasForRoom().forEach(System.out::println);
                    inventoryManager.addClueForEnigma();
                    break;
                case 4:
                    // TODO: Room manager show all rooms
                    inventoryManager.addDecorationToRoom();
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    int selectedDelete = 0; // TODO: Open discussion - Debería de estar room dentro de inventory manager? También facilitará el desarrollo del inventory (menu 5 y 6)
                    try {
                        selectedDelete = menuDelete();
                    } catch (IncorrectMenuOptionException e) {
                        System.out.println(e.getMessage());
                    }
                    switch (selectedDelete) {
                        case 1:
                            // TODO: RoomManager.delete
                            break;
                        case 2:
                            inventoryManager.deleteEnigma();
                            break;
                        case 3:
                            inventoryManager.deleteClue();
                            break;
                        case 4:
                            inventoryManager.deleteDecoration();
                            break;
                        case 5:
                            inventoryManager.deleteGift();
                            break;
                        default: break;
                    }
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

    public int menuDelete() throws IncorrectMenuOptionException {
        // TODO: Create a propper menu opion
        /*for (int i = 1; i <= MenuOptions.options.length; i++) {
            System.out.println( i + ". " + MenuOptions.options[i-1]);
        }
        System.out.println("0. " + MenuOptions.close);
        */


        int menuOption = Entry.readInt("Select a menu option between 1 and 5.");
        if (menuOption < 0 || menuOption > 5) throw new IncorrectMenuOptionException("Menu option should be between 0 and 5.");
        else return menuOption;
    }
}
