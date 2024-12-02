package connections;

import connections.attribute.outputAttribute.OutputAttribute;
import connections.attribute.outputAttribute.AttributeValue;
import exceptions.ConnectionException;
import connections.attribute.queryAttribute.QueryAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DbConnectionImpl implements DbConnection {
    private static final String FILENAME = "src/Password.txt";
    private static final String DRIVERCLASS = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/db-escaperoom";
    private static final String USER = "root";
    private String password;

    private static final DbConnectionImpl dbConnection = new DbConnectionImpl();

    public static DbConnectionImpl getInstance() {
        return dbConnection;
    }

    private DbConnectionImpl() {
        try {
            this.password = readPassword();
        } catch (IOException e) {
            System.err.println("Error. Could not read the file.");
        }
    }

    @Override
    public void create(String query, List<QueryAttribute> queryAttributes) { callUpdate(query, queryAttributes); }

    @Override
    public void delete(String query, List<QueryAttribute> queryAttributes) {
        callUpdate(query, queryAttributes);
    }

    @Override
    public List<HashSet<OutputAttribute>> query(String query, List<QueryAttribute> queryAttributes, List<OutputAttribute> outputAttributes) {
        return callQuery(query, queryAttributes, outputAttributes);
    }

    private void callUpdate(String query, List<QueryAttribute> queryAttributes) {
        try(Connection connection = this.createConnection();
            PreparedStatement statement = this.createStatementWithQuery(connection, query)) {
            this.addAttributesToStatement(statement, queryAttributes);
            this.executeUpdate(statement);
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<HashSet<OutputAttribute>> callQuery(String query, List<QueryAttribute> queryAttributes, List<OutputAttribute> attributes) {

        List<HashSet<OutputAttribute>> list = new ArrayList<>();

        try(Connection connection = this.createConnection();
            PreparedStatement statement = this.createStatementWithQuery(connection, query)) {

            this.addAttributesToStatement(statement, queryAttributes);

            try(ResultSet resultSet = this.executeQuery(statement)) {
                while(resultSet.next()) {
                    HashSet<OutputAttribute> hashSet = new HashSet<>();
                    for (OutputAttribute attribute: attributes) {
                        OutputAttribute att = new OutputAttribute(attribute.getName(), attribute.getType());
                        switch (attribute.getType()) {
                            case STRING -> {
                                AttributeValue<String> value = new AttributeValue<>(resultSet.getString(attribute.getName()));
                                att.setValue(value);
                            }
                            case INT -> {
                                AttributeValue<Integer> value = new AttributeValue<>(resultSet.getInt(attribute.getName()));
                                att.setValue(value);
                            }
                            case DOUBLE ->{
                                AttributeValue<Double> value = new AttributeValue<>(resultSet.getDouble(attribute.getName()));
                                att.setValue(value);
                            }
                        }
                        hashSet.add(att);
                    }
                    list.add(hashSet);
                }
            }
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    private void executeUpdate(PreparedStatement statement) throws ConnectionException {
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ConnectionException("Error creating the object.");
        }
    }

    private ResultSet executeQuery(PreparedStatement statement) throws ConnectionException {
        try {
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new ConnectionException("Error executing the query into de ddbb.");
        }
    }

    private Connection createConnection() throws ConnectionException {
        try {
            Class.forName(DRIVERCLASS);
            return DriverManager.getConnection(URL, USER, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ConnectionException("Error while attempting connection to the database.");
        }
    }

    private PreparedStatement createStatementWithQuery(Connection connection, String query) throws ConnectionException {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new ConnectionException("Error while creating statement from query " + query);
        }
    }

    private void addAttributesToStatement(PreparedStatement statement, List<QueryAttribute> attributes) throws ConnectionException {
        for (QueryAttribute attribute: attributes) {
            try {
                attribute.addToStatement(statement);
            } catch (ConnectionException e) {
                throw new ConnectionException(e.getMessage());
            }
        }
    }

    private static String readPassword() throws IOException {
        Path fileName = Path.of(DbConnectionImpl.FILENAME);
        return Files.readString(fileName);
    }
}
