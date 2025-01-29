package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util db;
    private Connection connection;

    public UserDaoJDBCImpl() {
        db = new Util();
        connection = db.getConnection();
    }

    public void createUsersTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS `katabd`.`users` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(100) NOT NULL,\n" +
                "  `lastName` VARCHAR(100) NOT NULL,\n" +
                "  `age` TINYINT NOT NULL,\n" +
                "  PRIMARY KEY (`id`));\n";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
            System.out.println("Users table is created");
        } catch (SQLException e) {
            System.err.println("Error in createUsersTable() " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS `katabd`.`users`";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(dropTableQuery);
            System.out.println("Users table is dropped");
        } catch (SQLException e) {
            System.err.println("Error in dropUsersTable() " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        // может должен быть какой то метод который проверяет если база данных существует
        String insertUserQuery = "INSERT INTO `katabd`.`users` (name, lastName, age)" +
                " VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.printf("User %s %s %d is saved\n", name, lastName, age);

        } catch (SQLException e) {
            System.err.println("Error in insert users  " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String removeUserByIdQuery = "DELETE FROM `katabd`.`users` WHERE `id` = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(removeUserByIdQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            System.out.printf("User with id %d was deleted.", id);

        } catch (SQLException e) {
            System.err.println("Error in removeUserById() " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String selectAllUsersQuery = "SELECT * FROM `katabd`.`users`";
        List<User> users = new ArrayList<User>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsersQuery);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error in selectAllUsers() " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanUsersTableQuery = "DELETE FROM `katabd`.`users`";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(cleanUsersTableQuery);
            System.out.println("Users table is cleaned");
        } catch(SQLException e){
            System.err.println("Error in cleanUsersTable() " + e.getMessage());
        }
    }
}
