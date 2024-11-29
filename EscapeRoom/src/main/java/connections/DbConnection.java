package connections;

import connections.query.resultAttribute.Attribute;
import connections.query.resultAttribute.AttributeValue;
import exceptions.ConnectionException;
import connections.query.queryAttribute.QueryAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DbConnection {

    protected Connection connection;
    protected ResultSet resultSet;
    protected PreparedStatement statement;

    private static final String FILENAME = "src/Password.txt";
    private static final String DRIVERCLASS = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/db-escaperoom";
    private static final String USER = "root";
    private String password;

    // TODO: Check try con resources

    private static final DbConnection dbConnection = new DbConnection();

    public static DbConnection getInstance() {
        return dbConnection;
    }

    private DbConnection() {
        try {
            this.password = readPassword();
        } catch (IOException e) {
            System.err.println("Error. Could not read the file.");
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void callCreate(String query, List<QueryAttribute> queryAttributes) {
        try {
            this.createConnection();
            this.createStatementWithQuery(query);
            this.addAttributesToStatement(queryAttributes);
            this.executeCreate();
        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public List<HashSet<Attribute>> callQuery(String query, List<QueryAttribute> queryAttributes, List<Attribute> attributes) {

        List<HashSet<Attribute>> list = new ArrayList<>();

        try {
            this.createConnection();
            this.createStatementWithQuery(query);
            this.addAttributesToStatement(queryAttributes);
            this.resultSet = this.executeQuery();

            while(this.resultSet.next()) {
                HashSet<Attribute> hasSet = new HashSet<>();
                for (Attribute attribute: attributes) {

                    switch (attribute.getType()) {
                        case STRING -> {
                            AttributeValue<String> value = new AttributeValue<>(this.resultSet.getString(attribute.getName()));
                            attribute.setValue(value);
                        }
                        case INT -> {
                            AttributeValue<Integer> value = new AttributeValue<>(this.resultSet.getInt(attribute.getName()));
                            attribute.setValue(value);
                        }
                        case DOUBLE ->{
                            AttributeValue<Double> value = new AttributeValue<>(this.resultSet.getDouble(attribute.getName()));
                            attribute.setValue(value);
                        }
                    }
                    hasSet.add(attribute);
                }
                list.add(hasSet);
            }
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection();
        }

        return list;
    }

    private void executeCreate() throws ConnectionException {
        try {
            this.statement.executeUpdate();
        } catch (SQLException e) {
            throw new ConnectionException("Error executing statement to create an object.");
        }
    }

    private ResultSet executeQuery() throws ConnectionException {
        try {
            this.resultSet = this.statement.executeQuery();
            return this.resultSet;
        } catch (SQLException e) {
            throw new ConnectionException("Error executing statement to query into de ddbb.");
        }
    }

    private void createConnection() throws ConnectionException {
        try {
            Class.forName(DRIVERCLASS);
            this.connection = DriverManager.getConnection(URL, USER, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ConnectionException("Error while attempting connection to the database.");
        }
    }

    private void createStatementWithQuery(String query) throws ConnectionException {
        try {
            this.statement = connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new ConnectionException("Error while creating statement from query " + query);
        }
    }

    private void addAttributesToStatement(List<QueryAttribute> attributes) throws ConnectionException {
        for (QueryAttribute attribute: attributes) {
            try {
                attribute.addToStatement(this.statement);
            } catch (ConnectionException e) {
                throw new ConnectionException(e.getMessage());
            }
        }
    }

    private void closeResultSet() {
        try {
            resultSet.close();
        } catch (SQLException ex) {
            System.err.println("Error. Couldn't close resultSet.");
        }
    }

    private void closeStatement() {
        try {
            statement.close();
        } catch (SQLException ex) {
            System.err.println("Error. Couldn't close statement.");
        }
    }

    private void closeConnection() {
        try {
            if (resultSet != null) {
                closeResultSet();
            }
            if (statement != null) {
                closeStatement();
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error. Couldn't close the connection properly.");
        }
    }

    private static String readPassword() throws IOException {
        Path fileName = Path.of(DbConnection.FILENAME);
        return Files.readString(fileName);
    }
}
