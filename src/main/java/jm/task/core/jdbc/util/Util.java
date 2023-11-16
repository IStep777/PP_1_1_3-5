package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    //private static final String DB_DRIVER ="com.mysql.cj.jdbc.Driver";
    private static final String DB_URL ="jdbc:mysql://localhost:3306/mydbtest?autoReconnect=true&useSSL=false";
    private static final String DB_USERNAME ="root1";
    private static final String DB_PASSWORD ="root1";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

    public static void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
