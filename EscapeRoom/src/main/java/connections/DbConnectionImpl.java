package connections;

import classes.enums.Level;
import classes.enums.Material;
import classes.enums.Theme;
import connections.attribute.Attribute;
import exceptions.ConnectionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

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
    public void create(String query, List<Attribute> queryAttributes) {
        this.callUpdate(query, queryAttributes);
    }

    @Override
    public void delete(String query, List<Attribute> queryAttributes) {
        this.callUpdate(query, queryAttributes);
    }

    @Override
    public List<HashSet<Attribute>> query(String query, List<Attribute> queryAttributes, List<Attribute> outputAttributes) {
        List<HashSet<Attribute>> list = new ArrayList<>();

        try(Connection connection = this.createConnection();
            PreparedStatement statement = this.createStatementWithQuery(connection, query)) {

            this.addAttributesToStatement(statement, queryAttributes);

            try(ResultSet resultSet = this.executeQuery(statement)) {
                while(resultSet.next()) {
                    HashSet<Attribute> hashSet = new HashSet<>();

                    for (Attribute attribute: outputAttributes) {
                        if (attribute.getType() == String.class) {
                            Attribute<String> att = new Attribute<>();
                            att.setName(attribute.getName());
                            att.setValue(resultSet.getString(attribute.getName()));
                            hashSet.add(att);
                        } else if (attribute.getType() == Integer.class) {
                            Attribute<Integer> att = new Attribute<>();
                            att.setName(attribute.getName());
                            att.setValue(resultSet.getInt(attribute.getName()));
                            hashSet.add(att);
                        } else if (attribute.getType() == Double.class) {
                            Attribute<Double> att = new Attribute<>();
                            att.setName(attribute.getName());
                            att.setValue(resultSet.getDouble(attribute.getName()));
                            hashSet.add(att);
                        } else if (attribute.getType() == Theme.class) {
                            Attribute<Theme> att = new Attribute<>();
                            att.setName(attribute.getName());
                            att.setValue(Theme.valueOf(resultSet.getString(attribute.getName())));
                            hashSet.add(att);
                        } else if (attribute.getType() == Material.class) {
                            Attribute<Material> att = new Attribute<>();
                            att.setName(attribute.getName());
                            att.setValue(Material.valueOf(resultSet.getString(attribute.getName())));
                            hashSet.add(att);
                        } else if (attribute.getType() == Level.class) {
                            Attribute<Level> att = new Attribute<>();
                            att.setName(attribute.getName());
                            att.setValue(Level.valueOf(resultSet.getString(attribute.getName())));
                            hashSet.add(att);
                        }
                    }
                    list.add(hashSet);
                }
            }
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    private void callUpdate(String query, List<Attribute> queryAttributes) {
        try(Connection connection = this.createConnection();
            PreparedStatement statement = this.createStatementWithQuery(connection, query)) {
            this.addAttributesToStatement(statement, queryAttributes);
            this.executeUpdate(statement);
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void executeUpdate(PreparedStatement statement) throws ConnectionException {
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ConnectionException("Error creating or deleting the object.");
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

    private void addAttributesToStatement(PreparedStatement statement, List<Attribute> queryAttributes) {

        for (int i = 0; i < queryAttributes.size(); i++) {
            Attribute attribute = queryAttributes.get(i);
            try {
                if (attribute.getType() == String.class) {
                    statement.setString(i + 1, (String) attribute.getValue());
                } else if (attribute.getType() == Integer.class) {
                    statement.setInt(i + 1, (Integer) attribute.getValue());
                } else if (attribute.getType() == Double.class) {
                    statement.setDouble(i + 1, (Double) attribute.getValue());
                } else if (attribute.getType() == LocalDateTime.class){
                    statement.setObject(i +1, attribute.getValue());
                } else if (attribute.getType() == Boolean.class){
                    statement.setBoolean(i +1, (Boolean) attribute.getValue());
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Could not set value to statement. Position: " + (i + 1) + " Attribute: " + attribute.getName() + " Value: " + attribute.getValue());
            }
        }
    }

    private static String readPassword() throws IOException {
        Path fileName = Path.of(DbConnectionImpl.FILENAME);
        return Files.readString(fileName);
    }
}
