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
        Integer escapeRoomId = escapeRoomManager.getEscapeRoom().getIdEscaperoom();

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
                    inventoryManager.showTotalInventoryValue(escapeRoomId);
                    break;
                case 7:
                    inventoryManager.deleteMenuStart();
                    break;
                case 8:
                    //userManager.start();
                    break;
                case 9:
                    break;
                case 10:
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
