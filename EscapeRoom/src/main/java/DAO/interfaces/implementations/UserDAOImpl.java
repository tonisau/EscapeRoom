package DAO.interfaces.implementations;

import DAO.interfaces.UserDAO;
import classes.User;
import connections.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends DbConnection implements UserDAO {
    DbConnection dbConnection = new DbConnection();

    @Override
    public User getUser(int id) {
        final String SQL = "SELECT * FROM user WHERE iduser = ? LIMIT 1";
        User user = null;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(SQL)) {
            pStatement.setInt(1, id);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()){
                int iduser = rs.getInt("iduser");
                String name = rs.getString("name");
                boolean isSubscriber = rs.getBoolean("isSubscriber");
                user = new User(iduser, name, isSubscriber);
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al obtener el usuario con id: " + id + "." + e.getMessage());
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        if(getUser(user.getId()) == null){
            System.out.println("No se ha podido actualizar el usuario, no se encuentra en la base de datos.");
        }else{
            final String SQL = "UPDATE user" +
                    " SET name = ?, email = ?, isSubscriber = ? WHERE iduser = ?";
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement pStatement = connection.prepareStatement(SQL)) {
                pStatement.setString(1, user.getName());
                pStatement.setString(2, user.getEmail());
                pStatement.setBoolean(3, user.isSuscriber());
                pStatement.setInt(4, user.getId());
                pStatement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Ocurrió un error al actualizar el usuario." + e.getMessage());
            }
        }
    }

    @Override
    public List<String> getCertificates(User user) {
        return List.of();
    }

    @Override
    public List<String> getGifts(User user) {
        return List.of();
    }

    @Override
    public void add(User user) {
        DbConnection dbConnection = new DbConnection();
        final String SQL = "INSERT INTO user (name, email) VALUES (?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(SQL)) {
            pStatement.setString(1, user.getName());
            pStatement.setString(2, user.getEmail());
            pStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error al guardar el usuario." + e.getMessage());
        }
    }

    @Override
    public List<User> showData() {
        User user;
        List<User> users = new ArrayList<>();
        final String SQL = "SELECT * FROM user";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(SQL)) {
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("iduser");
                String name = rs.getString("name");
                boolean isSubscriber = rs.getBoolean("isSubscriber");
                user = new User(id, name, isSubscriber);
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de usuarios. " + e.getMessage());
        }
        return users;
    }
}
