package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoHibernateImpl implements UserDao {




    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users(\n" +
                    "   id       INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
                    "   name     VARCHAR(45)    NOT NULL,\n"
                    + "   lastname VARCHAR(100)   NOT NULL,\n"
                    + "   age      TINYINT          NOT NULL)";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица Users создана");
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица Users удалена");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем: " + name + " добавлен в базу данных");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("User с id: " + id + " удален из БД");
        }


    }

    @Override
    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            listUser = session.createSQLQuery("SELECT ID, NAME, LASTNAME, AGE FROM users").addEntity(User.class).list();
            session.getTransaction().commit();
        } finally {
            return listUser;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE users").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица Users очищена");
        }

    }
}
