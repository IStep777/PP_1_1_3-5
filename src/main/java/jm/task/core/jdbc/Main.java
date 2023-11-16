package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {

        userService.createUsersTable();
        userService.saveUser("Petr", "Petrov", (byte) 33);
        userService.saveUser("Ivan", "Ivanov", (byte) 22);
        userService.saveUser("Dasha", "Schevchenko", (byte) 11);
        userService.saveUser("Vlad", "Kolov", (byte) 44);
        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
