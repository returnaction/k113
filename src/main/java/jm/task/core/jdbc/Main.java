package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Mike", "Spencer", (byte) 43);
        userService.saveUser("Nikita", "Obergan", (byte) 33);
        userService.saveUser("Leonel", "Gustavo", (byte) 13);
        userService.saveUser("Ivan", "Petrov", (byte) 23);

        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
