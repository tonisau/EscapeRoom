package managers;

import classes.EscapeRoom;
import connections.DbConnection;
import exceptions.ConnectionException;
import utils.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {

    private static final ConnectionManager connectionManager = new ConnectionManager();

    private ConnectionManager() {}

    public static ConnectionManager getInstance() {
        return connectionManager;
    }

    public void createEscapeRoom(EscapeRoom escapeRoom)  {
        try {
            DbConnection dbConnection = new DbConnection();
            PreparedStatement stmt = this.checkConnection(dbConnection).prepareStatement(Query.CREATEESCAPEROOM);
            stmt.setString(1, escapeRoom.getName());
            stmt.setString(2, escapeRoom.getCif());
            dbConnection.executeStatementUpdate(stmt);
        } catch (SQLException | ConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<EscapeRoom> getAllEscapeRooms() {
        List<EscapeRoom> list = new ArrayList<>();
        try {
            DbConnection dbConnection = new DbConnection();
            PreparedStatement stmt = this.checkConnection(dbConnection).prepareStatement(Query.GETESCAPEROOM);
            ResultSet res = dbConnection.executeStatementQuery(stmt);

            while (res.next()) {
                int id = res.getInt("idEscapeRoom");
                String name = res.getString("name");
                String cif = res.getString("cif");

                EscapeRoom escapeRoom = new EscapeRoom(id, name, cif);
                list.add(escapeRoom);
            }

        } catch (SQLException | ConnectionException e) {
            System.out.println(e.getMessage());
        } finally {
            return list;
        }
    }

    private Connection checkConnection(DbConnection dbConnection) throws ConnectionException {
        Connection connection = dbConnection.getConnection();
        if (connection == null) throw new ConnectionException("Connection failed");
        return  connection;
    }
}
