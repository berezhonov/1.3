/*
    пусть здесь будет проверка работы БД
 */

package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        // Создание таблицы
        userService.createUsersTable();

        // Добавление пользователей
        userService.saveUser("Ivan", "Ivanov", (byte) 5);
        userService.saveUser("Nikitos", "Wernin", (byte) 30);
        userService.saveUser("Anna", "Plomova", (byte) 20);
        userService.saveUser("Nataly", "Ass", (byte) 66);

        // Получение всех пользователей и вывод их на консоль
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

        // Очистка таблицы
        userService.cleanUsersTable();

        // Удаление таблицы
        userService.dropUsersTable();
    }
}
//