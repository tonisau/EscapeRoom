package managers;

import org.junit.jupiter.api.*;


class InventoryManagerTest {

    InventoryManager inventoryManager;
    ObservableMock observable;
    RoomDAOMock roomDAO;

    @BeforeAll
    static void initAll() {

    }

    @BeforeEach
    void init() {
        observable = new ObservableMock();
        roomDAO = new RoomDAOMock();
        inventoryManager = InventoryManager.getInstance();
        inventoryManager.setRoomHelper(new RoomHelperMock());
        inventoryManager.setRoomDAO(roomDAO);
        inventoryManager.setObservable(observable);
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

/*

    @Test
    public void givenStaticMockRegistration_whenMocked_thenReturnsMockSuccessfully() {
        //assertTrue(Mockito.mockingDetails(EntryTest.class).isMock());
    }

    @Test
    void addNewEnigma() {
    }

    @Test
    void addNewDecoration() {
    }

    @Test
    void createGift() {
    }

    @Test
    void deleteGift() {
    }

    @Test
    void addNewClue() {
    }

    @Test
    void showInventory() {
    }

    @Test
    void showTotalInventoryValue() {
    }

    @Test
    void deleteRoom() {
    }

    @Test
    void deleteEnigma() {
    }

    @Test
    void deleteDecoration() {
    }

    @Test
    void deleteClue() {
    }
    */

    @AfterEach
    void tearDown() {
        //utilities.close();
    }
}