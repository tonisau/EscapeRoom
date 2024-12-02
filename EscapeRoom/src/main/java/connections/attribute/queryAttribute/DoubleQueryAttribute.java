package connections.query.queryAttribute;

import connections.attribute.queryAttribute.QueryAttribute;
import exceptions.ConnectionException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoubleQueryAttribute implements QueryAttribute {

    int position;
    double value;

    public DoubleQueryAttribute(int position, double value) {
        this.position = position;
        this.value = value;
    }

    @Override
    public void addToStatement(PreparedStatement statement) throws ConnectionException {
        try {
            statement.setDouble(position, value);
        } catch (SQLException e) {
            throw new ConnectionException("Could not set double to statement " + value);
        }
    }
}
