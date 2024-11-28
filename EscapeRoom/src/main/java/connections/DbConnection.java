package connections;

import classes.EscapeRoom;
import exceptions.ConnectionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class DbConnection {

    protected Connection connection;
    protected ResultSet resultSet;
    protected PreparedStatement statement;
    private static final String FILENAME = "src/Password.txt";
    private static final String URL = "jdbc:mysql://localhost:3306/db-escaperoom";
    private static final String USER = "root";
    private String password;

    public DbConnection() {
        try {
            this.password = readPassword();
            this.connection = createConnection();
        } catch (IOException e) {
            System.err.println("Error. Could not read the file.");
        } catch (ConnectionException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    public void executeStatementUpdate(PreparedStatement statement) {
        this.statement = statement;
        try {
            this.statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public ResultSet executeStatementQuery(PreparedStatement statement) {
        this.statement = statement;
        try {
            this.resultSet = this.statement.executeQuery();
            return this.resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //closeConnection();
        }
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void closeResultSet() {
        try {
            resultSet.close();
        } catch (SQLException ex) {
            System.err.println("Error. Couldn't close resultSet.");
        }
    }

    public void closeStatement() {
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

    private Connection createConnection() throws ConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ConnectionException("Error while attempting connection to the database.");
        }
    }

    private static String readPassword() throws IOException {
        Path fileName = Path.of(DbConnection.FILENAME);
        return Files.readString(fileName);
    }
}
