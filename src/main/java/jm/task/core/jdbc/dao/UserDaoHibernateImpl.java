package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sqlCreateTableQuery = "CREATE TABLE IF NOT EXISTS `katabd`.`users` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(100) NOT NULL,\n" +
                "  `lastName` VARCHAR(100) NOT NULL,\n" +
                "  `age` TINYINT NOT NULL,\n" +
                "  PRIMARY KEY (`id`));\n";

        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(sqlCreateTableQuery).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Users table is created");
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlDropTableQeury = "DROP TABLE IF EXISTS `katabd`.`users`";
        try(Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createNativeQuery(sqlDropTableQeury).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Users table is deleted.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("New User " + name + " " + lastName + " " + age + " saved");
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if(user != null){
                session.delete(user);
                System.out.println("User " + user.getId() + " " + user.getLastName() + " " + user.getAge() + " removed");
            }
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.getSessionFactory().openSession()){
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session  = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
