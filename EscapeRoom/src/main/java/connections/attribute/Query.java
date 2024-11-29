package connections.attribute;

public class Query {
    public static final String CREATEESCAPEROOM = "INSERT INTO escaperoom (name, cif) VALUES (?, ?)";
    public static final String GETESCAPEROOM = "SELECT * FROM escaperoom LIMIT 1";
    public static final String DELETEESCAPEROOM = "DELETE escaperoom WHERE idescaperoom = ?";
}
