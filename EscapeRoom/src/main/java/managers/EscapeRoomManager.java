package managers;

import exceptions.IncorrectMenuOptionException;
import utils.Entry;

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
        System.out.println(
                "Menú:" +
                        "\n1.- Create new Escape Room " +
                        "\n2.- Add new Room to the Escape Room. " +
                        "\n3.- Add new Enigma to a Room." +
                        "\n4.- Add new Clue for an Enigma. " +
                        "\n5.- Add new Decoration object to a/some Room(s). " +
                        "\n6.- Show inventory (Rooms, Enigmas, Clues and Decorations). " +
                        "\n7.- Show total inventory value." +
                        "\n8 - Remove Room, Enigma, Clue or Decoration. " +
                        "\n9 - Add new user. " +
                        "\n10 - Create new game and generate ticket. " +
                        "\n11 - Show tota sales. " +
                        "\n11 - Generate certificate for user. " +
                        "\n0.- Close."
        );

        int menuOption = Entry.readInt("Select a menu option between 0 and 11");
        if (menuOption < 0 || menuOption > 11) throw new IncorrectMenuOptionException("Menu option should be between 0 and 11.");
        else return menuOption;
    }
}
