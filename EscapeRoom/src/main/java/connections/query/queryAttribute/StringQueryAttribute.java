package connections.query.queryAttribute;

import exceptions.ConnectionException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringQueryAttribute implements QueryAttribute {

    int position;
    String value;

    public StringQueryAttribute(int position, String value) {
        this.position = position;
        this.value = value;
    }

    @Override
    public void addToStatement(PreparedStatement statement) throws ConnectionException {
        try {
            statement.setString(position, value);
        } catch (SQLException e) {
            throw new ConnectionException("Could not set String to statement " + value);
        }
    }
}
