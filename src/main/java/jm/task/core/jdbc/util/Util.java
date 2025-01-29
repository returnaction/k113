package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String HOST = "jdbc:mysql://localhost:3306/katabd";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private Connection connection;
    public Util() {
        try{
            connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }
}
