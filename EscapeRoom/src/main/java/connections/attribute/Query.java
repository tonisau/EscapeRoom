package connections.attribute;

public class Query {
    public static final String CREATEESCAPEROOM = "INSERT INTO escaperoom (name, cif) VALUES (?, ?)";
    public static final String GETESCAPEROOM = "SELECT * FROM escaperoom LIMIT 1";
    public static final String DELETEESCAPEROOM = "DELETE escaperoom WHERE idescaperoom = ?";

    public static final String CREATEENIGMA = "INSERT INTO enigma (name, price, room_idroom) VALUES (?, ?, ?)";
    public static final String GETENIGMABYROOM = "SELECT idenigma, name, price FROM enigma WHERE room_idroom = ?";
    public static final String DELETEENIGMA = "DELETE FROM enigma WHERE idenigma = ?";

    public static final String CREATECLUE = "INSERT INTO clue (name, price, theme, enigma_idenigma) VALUES (?, ?, ?, ?)";

    public static final String CREATEUSER = "INSERT INTO user (name, email) VALUES (?, ?)";;
    public static final String UPDATEUSER = "UPDATE user" +
            " SET name = ?, email = ?, isSubscriber = ? WHERE iduser = ?";
    public static final String GETUSER = "SELECT * FROM user WHERE iduser = ? LIMIT 1";
    public static final String GETUSERBYEMAIL = "SELECT * FROM user WHERE email = ? LIMIT 1";
    public static final String GETCERTIFICATES = "SELECT e.name FROM user_has_enigma uhe " +
            "JOIN user u ON uhe.user_iduser = u.iduser " +
            "JOIN enigma e ON uhe.enigma_idenigma = e.idenigma " +
            "WHERE u.iduser = ?";
    public static final String GETGIFTS = "SELECT g.name FROM user_has_gift uhg " +
            "JOIN user u ON uhg.user_iduser = u.iduser " +
            "JOIN gift g ON uhg.gift_idgift = g.idgift " +
            "WHERE u.iduser = ?";
    public static final String SHOWUSERS = "SELECT * FROM user";
}