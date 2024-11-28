package utils;

public class Query {
    public static final String CREATEESCAPEROOM = "INSERT INTO escaperoom (name, cif) VALUES (?, ?)";
    public static final String GETESCAPEROOM = "SELECT * FROM escaperoom";
}
