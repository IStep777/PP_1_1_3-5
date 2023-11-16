package jm.task.core.jdbc.dao;

import com.mysql.cj.protocol.Resultset;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl extends Util implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(\n" +
                    "   id       INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
                    "   name     VARCHAR(45)    NOT NULL,\n"
                    + "   lastname VARCHAR(100)   NOT NULL,\n"
                    + "   age      TINYINT          NOT NULL)");
            System.out.println("Таблица Users создана");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось созда таблицу в БД");
        }

    }


    public void dropUsersTable() {

        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Таблица Users удалена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить таблицу в БД");
        }

    }


    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "INSERT INTO users (NAME,LASTNAME,AGE) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, String.valueOf(age));
            preparedStatement.executeUpdate();
            System.out.println("User с именем: " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось добавить запись в БД");
        }
    }


    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "DELETE FROM users WHERE id = ?")) {
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();
            System.out.println("User с id: " + id + " удален");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить запись в БД");
        }

    }

    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultset = statement.executeQuery("SELECT ID, NAME, LASTNAME, AGE FROM users");

            while (resultset.next()) {
                User user = new User();
                user.setName(resultset.getString("NAME"));
                user.setLastName(resultset.getString("LASTNAME"));
                user.setAge(resultset.getByte("AGE"));
                user.setId(resultset.getLong("ID"));
                System.out.println(user.toString());
                listUser.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось прочитать таблицу БД");
        } finally {
            return listUser;
        }


    }


    public void cleanUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("TRUNCATE users");
            System.out.println("Таблица Users очищена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очитстить таблицу в БД");
        }
    }
}
