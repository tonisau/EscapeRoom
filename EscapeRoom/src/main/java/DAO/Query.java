package DAO;

public class Query {
    public static final String CREATEESCAPEROOM = "INSERT INTO escaperoom (name, cif) VALUES (?, ?)";
    public static final String GETESCAPEROOM = "SELECT * FROM escaperoom LIMIT 1";
    public static final String DELETEESCAPEROOM = "DELETE escaperoom WHERE idescaperoom = ?";

    public static final String CREATEENIGMA = "INSERT INTO enigma (name, price, room_idroom) VALUES (?, ?, ?)";
    public static final String GETENIGMABYROOM = "SELECT idenigma, name, price FROM enigma WHERE room_idroom = ?";
    public static final String GETALLENIGMAS = "SELECT idenigma, name, price FROM enigma";
    public static final String DELETEENIGMA = "DELETE FROM enigma WHERE idenigma = ?";
    public static final String GETENIGMABYID = "SELECT idenigma, name, price FROM enigma WHERE idenigma = ?";

    public static final String CREATECLUE = "INSERT INTO clue (name, price, theme, enigma_idenigma) VALUES (?, ?, ?, ?)";
    public static final String GETCLUEBYENIGMA = "SELECT idclue, name, price, theme FROM clue WHERE enigma_idenigma = ?;";
    public static final String GETALLCLUES = "SELECT idclue, name, price, theme FROM clue";
    public static final String DELETECLUE = "DELETE FROM clue WHERE idclue = ?";
    public static final String GETCLUEBYID = "SELECT idclue, name, price, theme FROM clue WHERE idclue = ?";

    public static final String CREATEDECORATION = "INSERT INTO decoration (name, material, price, quantity, room_idroom) VALUES (?, ?, ?, ?, ?)";
    public static final String GETDECORATIONEBYROOM = "SELECT iddecoration, name, material, price, quantity FROM decoration WHERE room_idroom = ?";
    public static final String GETALLDECORATIONS = "SELECT iddecoration, name, material, price, quantity FROM decoration";
    public static final String DELETEDECORATION = "DELETE FROM decoration WHERE iddecoration = ?";
    public static final String GETDECORATIONBYID = "SELECT iddecoration, name, material, price, quantity FROM decoration WHERE iddecoration = ?";

    public static final String CREATEGIFT = "INSERT INTO gift (name) VALUES (?)";
    public static final String CREATEUSERHASGIFT = "INSERT INTO user_has_gift (gift_idgift, user_iduser) VALUES (?, ?)";
    public static final String GETGIFTBYUSER = "select idgift, name from user_has_gift uhg join gift g ON uhg.gift_idgift = g.idgift where uhg.user_iduser = ?";
    public static final String GETALLGIFTS = "SELECT idgift, name FROM gift";
    public static final String DELETEGIFT = "DELETE FROM gift WHERE idgift = ?";
    public static final String GETGIFTBYID = "SELECT idgift, name FROM gift WHERE idgift = ?";

    public static final String CREATEROOM = "INSERT INTO room (name, price, level, escaperoom_idescaperoom) VALUES (?, ?, ?, ?)";
    public static final String GETROOMBYESCAPEROOM = "SELECT idroom, name, price, level FROM room WHERE escaperoom_idescaperoom = ?;";
    public static final String GETALLROOMS = "SELECT idroom, name, price, level FROM room";
    public static final String DELETEROOM = "DELETE FROM room WHERE idroom = ?";
    public static final String GETROOMBYID = "SELECT idroom, name, price, level FROM room WHERE idroom = ?";

    public static final String CREATEUSER = "INSERT INTO user (name, email) VALUES (?, ?)";;
    public static final String UPDATEUSER = "UPDATE user" +
            " SET name = ?, email = ?, isSubscriber = ? WHERE iduser = ?";
    public static final String GETUSER = "SELECT * FROM user WHERE iduser = ? LIMIT 1";
    public static final String SHOWUSERS = "SELECT * FROM user";
    public static final String GETUSERSWITHENIGMA = "SELECT iduser, name, email FROM user INNER JOIN user_has_enigma ON user.iduser = user_has_enigma.user_iduser WHERE user_has_enigma.enigma_idenigma = ?";
    public static final String DELETEUSERHASENIGMA = "DELETE FROM user_has_enigma WHERE enigma_idenigma = ?";
    public static final String GETENIGMASBYUSER = "SELECT e.name FROM user_has_enigma uhe " +
            "JOIN enigma e ON uhe.enigma_idenigma = e.idenigma " +
            "WHERE user_iduser = ?";

    public static final String CREATETICKET = "INSERT INTO ticket (price, date, room_idroom) VALUES (?, ?, ?)";
    public static final String GETTICKETINCOME= "SELECT SUM(price) as Income FROM ticket WHERE Date BETWEEN ? and ?";
    public static final String CREATEUSER_HAS_ENIGMAS = "INSERT INTO user_has_enigma (enigma_idenigma, user_iduser) VALUES (?, ?)";


}
