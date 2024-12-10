package managers;

import classes.Room;
import classes.enums.Level;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;


class InventoryManagerTest {

    InventoryManager inventoryManager;
    ObservableMock observable;
    RoomDAOMock roomDAO;
    Room room;

    @BeforeAll
    static void initAll() {

    }

    @BeforeEach
    void init() {
        room = new Room("Mistery room", 45.0, Level.LOW);
        observable = new ObservableMock();
        roomDAO = new RoomDAOMock();
        inventoryManager = InventoryManager.getInstance();
        inventoryManager.setRoomHelper(new RoomHelperMock(room));
        inventoryManager.setRoomDAO(roomDAO);
        inventoryManager.setObservable(observable);
    }

    @Test
    void givenAddRoomCalled_wheDAOCalled_ThenExpectedRoomSentToDDBB() {
        roomDAO.success = false;
        inventoryManager.addRoomToEscapeRoom(3);
        Assertions.assertEquals(roomDAO.room, this.room);
    }

    @Test
    void givenAddRoomCalled_whenDAOResponseFailed_ThenSubscribersNotNotified() {
        roomDAO.success = false;
        inventoryManager.addRoomToEscapeRoom(3);
        Assertions.assertFalse(observable.notified);
    }

    @Test
    void givenAddRoomCalled_whenDAOResponseSucceeds_ThenSubscribersGetNotified() {
        roomDAO.success = true;
        inventoryManager.addRoomToEscapeRoom(3);
        Assertions.assertTrue(observable.notified);
    }
}