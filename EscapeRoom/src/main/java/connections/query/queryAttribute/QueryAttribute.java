package connections.query.queryAttribute;

import exceptions.ConnectionException;

import java.sql.PreparedStatement;

public interface QueryAttribute {
    public void addToStatement(PreparedStatement statement) throws ConnectionException;
}
