package connections;

import connections.attribute.outputAttribute.OutputAttribute;
import connections.attribute.outputAttribute.AttributeValue;
import connections.attribute.Attribute;
import exceptions.ConnectionException;
import connections.attribute.queryAttribute.QueryAttribute;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
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
    public void create(String query, List<QueryAttribute> queryAttributes) { callUpdate(query, queryAttributes); }

    @Override
    public <T> void createWithReflection(String query, T object) {
        try(Connection connection = this.createConnection();
            PreparedStatement statement = this.createStatementWithQuery(connection, query)) {
            this.addAttributesToStatementReflection(statement, object);
            this.executeUpdate(statement);
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createWithGenerics(String query, List<Attribute> queryAttributes) {
        try(Connection connection = this.createConnection();
            PreparedStatement statement = this.createStatementWithQuery(connection, query)) {
            this.addAttributesToStatementGenerics(statement, queryAttributes);
            this.executeUpdate(statement);
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(String query, List<QueryAttribute> queryAttributes) {
        callUpdate(query, queryAttributes);
    }

    @Override
    public List<HashSet<Attribute>> query(String query, List<Attribute> queryAttributes, List<Attribute> outputAttributes) {
        List<HashSet<Attribute>> list = new ArrayList<>();

        try(Connection connection = this.createConnection();
            PreparedStatement statement = this.createStatementWithQuery(connection, query)) {

            this.addAttributesToStatementGenerics(statement, queryAttributes);

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
                        }
                    }
                    list.add(hashSet);
                }
            }
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;


        //return callQuery(query, queryAttributes, outputAttributes);
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

    private void addAttributesToStatementGenerics(PreparedStatement statement, List<Attribute> queryAttributes) {

        for (int i = 0; i < queryAttributes.size(); i++) {
            Attribute attribute = queryAttributes.get(i);
            try {
                if (attribute.getValue() instanceof String) {
                    statement.setString(i + 1, (String) attribute.getValue());
                } else if (attribute.getValue() instanceof Integer) {
                    statement.setInt(i + 1, (Integer) attribute.getValue());
                } else if (attribute.getValue() instanceof Double) {
                    statement.setDouble(i + 1, (Double) attribute.getValue());
                }
            } catch (SQLException e) {
                System.out.println("Could not set value to statement.Position: " + (i + 1) + " Attribute: " + attribute.getValue() + " Value: " + attribute.getName());
            }
        }
    }

    private <T> void addAttributesToStatementReflection(PreparedStatement statement, T object) {
        try {
            Class<?> c = object.getClass();
            Field[] fields = c.getDeclaredFields();
            int i = 1;
            for(Field field: fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();

                Object instance = field.get(object);
                if (instance == null) { continue; }
                else { i += 1; }

                System.out.println("Field: " + fieldName + ", Type: " + fieldType.getName());

                if (fieldType == Integer.class) statement.setInt(i, (Integer) instance);
                else if (fieldType == String.class) statement.setString(i, (String) instance);
                else if (fieldType == Double.class) statement.setDouble(i, (Double) instance);
                else System.out.println("Unsupported class.");
            }

        } catch (NullPointerException | SQLException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readPassword() throws IOException {
        Path fileName = Path.of(DbConnectionImpl.FILENAME);
        return Files.readString(fileName);
    }
}
