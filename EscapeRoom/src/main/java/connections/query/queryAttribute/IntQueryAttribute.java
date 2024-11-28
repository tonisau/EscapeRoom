package connections.query.queryAttribute;

import exceptions.ConnectionException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntQueryAttribute implements QueryAttribute {

    int position;
    int value;

    public IntQueryAttribute(int position, int value) {
        this.position = position;
        this.value = value;
    }

    @Override
    public PreparedStatement addToStatement(PreparedStatement statement) throws ConnectionException {
        try {
            statement.setInt(position, value);
            return statement;
        } catch (SQLException e) {
            throw new ConnectionException("Could not set int to statement " + value);
        }
    }
}
