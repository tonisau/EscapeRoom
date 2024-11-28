package connections;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class DbConnection {

    protected Connection connection;
    protected ResultSet resultSet;
    protected PreparedStatement statement;
    private static final String URL = "jdbc:mysql://localhost:3306/db-escaperoom";
    private static final String USER = "root";
    private String password;

    public DbConnection() {
        try {
            this.password = readPassword();

        } catch (IOException e) {
            System.err.println("Error. Could not read the file.");
        }
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error while attempting connection to the database.");
        }
        return connection;
    }

    public PreparedStatement getStatement() {
        return statement;
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

    public void closeConnection() {
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

    public static String readPassword() throws IOException {
        Path fileName = Path.of("src/Password.txt");
        String password = Files.readString(fileName);

        return password;
    }
}
