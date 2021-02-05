package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserServiceImpl userService = new UserServiceImpl();

        try {
            userService.createUsersTable();
            userService.saveUser("Антон", "Назарчук", (byte) 37);
            userService.saveUser("Маша", "Ака Бум", (byte) 22);
            userService.saveUser("Женя", "Шульц", (byte) 30);
            userService.saveUser("Гена", "Хромой", (byte) 44);

            List<User> users = userService.getAllUsers();
            users.forEach(System.out::println);

            userService.cleanUsersTable();
            userService.dropUsersTable();
            userService.connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
