package connections.query;

public class Query {
    public static final String CREATEESCAPEROOM = "INSERT INTO escaperoom (name, cif) VALUES (?, ?)";
    public static final String GETESCAPEROOM = "SELECT * FROM escaperoom LIMIT 1";

    public static final String CREATEENIGMA = "INSERT INTO enigma (name, price, room_idroom) VALUES (?, ?, ?)";
    public static final String GETENIGMABYROOM = "SELECT idenigma, name, price FROM enigma WHERE room_idroom = ?";
    public static final String DELETEENIGMA = "DELETE FROM enigma WHERE idenigma = ?";

    public static final String CREATECLUE = "INSERT INTO clue (name, price, theme, enigma_idenigma) VALUES (?, ?, ?, ?)";
}
