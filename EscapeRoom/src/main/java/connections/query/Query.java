package connections.query;

public class Query {
    public static final String CREATEESCAPEROOM = "INSERT INTO escaperoom (name, cif) VALUES (?, ?)";
    public static final String GETESCAPEROOM = "SELECT * FROM escaperoom LIMIT 1";

    public static final String CREATEENIGMA = "INSERT INTO enigma (name, price, room_idroom) VALUES (?, ?, ?)";
    public static final String GETENIGMABYROOM = "SELECT idenigma, name, price FROM enigma WHERE room_idroom = ?";
}
