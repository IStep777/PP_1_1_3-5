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
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(
                    "create table IF NOT EXISTS users(\n" +
                            "   id       int primary key AUTO_INCREMENT not null,\n" +
                            "   name     varchar(45)    not null,\n" +
                            "   lastname varchar(100)   null,\n" +
                            "   age      TINYINT          null)");
            System.out.println("Таблица Users создана");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось созда таблицу в БД");
        }
        finally {
            if (connection != null){
                Util.closeConnection(connection);
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(
                    "drop table IF EXISTS users");
            System.out.println("Таблица Users удалена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить таблицу в БД");
        }
        finally {
            if (connection != null){
                Util.closeConnection(connection);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        Connection connection = getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into users (NAME,LASTNAME,AGE) values (?, ?, ?)")){

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, String.valueOf(age));
            preparedStatement.executeUpdate();
            System.out.println("User с именем: " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось добавить запись в БД");
        }
        finally {
            if (connection != null){
                Util.closeConnection(connection);
                }
            }
        }



    public void removeUserById(long id) {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from users where id = ?")){
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();
            System.out.println("User с id: " + id + " удален");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить запись в БД");
        }
        finally {
            if (connection != null){
                Util.closeConnection(connection);
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = getConnection();
    List<User> listUser = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultset = statement.executeQuery(
                    "select ID, NAME, LASTNAME, AGE from users");

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
        }
        finally {
            if (connection != null){
                Util.closeConnection(connection);
            }
            return listUser;
        }

    }

    public void cleanUsersTable() {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("truncate users");
            System.out.println("Таблица Users очищена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очитстить таблицу в БД");
        }
        finally {
            if (connection != null){
                Util.closeConnection(connection);
            }
        }
    }
}
