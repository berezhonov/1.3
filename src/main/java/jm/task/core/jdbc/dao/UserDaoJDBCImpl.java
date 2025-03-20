package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Util.getConnection();
    }

    @Override
    public void createUsersTable() {
        String db = "CREATE TABLE IF NOT EXISTS User (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), lastName VARCHAR(100), age TINYINT)";
        try (Connection connection = Util.getConnection();
             Statement stat = connection.createStatement()) {
            stat.execute(db);
            System.out.println("Таблица пользователей создана.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String db = "DROP TABLE IF EXISTS User";
        try (Connection connection = Util.getConnection();
             Statement stat = connection.createStatement()) {
            stat.execute(db);
            System.out.println("Таблица пользователей удалена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser (String name, String lastName, byte age) {
        String db = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement prStat = connection.prepareStatement(db)) {
            prStat.setString(1, name);
            prStat.setString(2, lastName);
            prStat.setByte(3, age);
            prStat.executeUpdate();
            System.out.println("Пользователь с именем " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String db = "DELETE FROM User WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement prStat = connection.prepareStatement(db)) {
            prStat.setLong(1, id);
            prStat.executeUpdate();
            System.out.println("Пользователь с id " + id + " удален из базы данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String db = "SELECT * FROM User";
        try (Connection connection = Util.getConnection();
             Statement stat = connection.createStatement();
             ResultSet resultSet = stat.executeQuery(db)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        String db = "DELETE FROM User";
        try (Connection connection = Util.getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate(db);
            System.out.println("Таблица пользователей очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
