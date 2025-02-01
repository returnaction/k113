package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String HOST = "jdbc:mysql://localhost:3306/katabd";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private Connection connection;

    // имплементация с Hibernate
    private static SessionFactory sessionFactory;
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

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            sessionFactory = new Configuration()
                    .setProperty("hibernate.connection.url", HOST)
                    .setProperty("hibernate.connection.username", USER)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.hbm2ddl.auto", "update")
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }

        return sessionFactory;
    }
}
