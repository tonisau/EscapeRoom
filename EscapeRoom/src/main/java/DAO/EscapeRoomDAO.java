package DAO;

import classes.EscapeRoom;

import java.util.Optional;

public interface EscapeRoomDAO {
    void createEscapeRoom(EscapeRoom escapeRoom);
    public Optional<EscapeRoom> getEscapeRoomIfPresent();
}
