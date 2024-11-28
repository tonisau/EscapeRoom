package connections.query.queryAttribute;

import exceptions.ConnectionException;

import java.sql.PreparedStatement;

public interface QueryAttribute {
    public PreparedStatement addToStatement(PreparedStatement statement) throws ConnectionException;
}
