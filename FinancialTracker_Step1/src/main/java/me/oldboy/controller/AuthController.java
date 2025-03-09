package me.oldboy.controller;

import me.oldboy.entity.User;
import me.oldboy.entity.feature.Auth;
import me.oldboy.entity.feature.Role;
import me.oldboy.exception.WrongEnteredDataFormatException;
import me.oldboy.dao.UserDao;

import java.util.Scanner;

/**
 * Controller class that serves as an intermediary between the application's view and various services.
 * Handles user registration and login process.
 */
public class AuthController {
    private static AuthController INSTANCE;

    public static AuthController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new AuthController();
        }
        return INSTANCE;
    }

    private UserDao userDao = UserDao.getInstance();

    public User regUser(Scanner scanner){
        System.out.println("\nРегистрация нового пользователя.");
        String[] registrationData = regMenu(scanner);

        User regUser = User.builder()
                           .email(registrationData[0])
                           .password(registrationData[1])
                           .userName(registrationData[2])
                           .auth(Auth.IS_ACTIVE)
                           .role(Role.USER)
                           .build();

        return userDao.save(regUser);
    }

    public User login(Scanner scanner){
        System.out.println("\nВход в систему.");
        String[] loginData = enterLoginAndPassMenu(scanner);

        return userDao.findByEmailAndPassword(loginData[0], loginData[1]);
    }

    private String[] enterLoginAndPassMenu(Scanner scanner){
        String[] loginAndPass = new String[3];

        System.out.print("Введите email: ");
        String enterEmail = scanner.nextLine().trim();
        if(enterEmail.matches("\\w+@\\w+.(ru|com)")){
            loginAndPass[0] = enterEmail;
        } else {
            throw new WrongEnteredDataFormatException("Not email was entered!");
        }

        System.out.print("Введите пароль: ");
        String enterPassword = scanner.nextLine().trim();
        if(enterPassword.matches("\\s+")){
            throw new WrongEnteredDataFormatException("Password cannot contain only spaces!");
        } else {
            loginAndPass[1] = enterPassword;
        }

        return loginAndPass;
    }

    private String[] regMenu(Scanner scanner){
        String[] loginAndPass = enterLoginAndPassMenu(scanner);

        System.out.print("Введите имя: ");
        String enterName = scanner.nextLine().trim();
        if(enterName.matches("\\s+")){
            throw new WrongEnteredDataFormatException("Name cannot contain only spaces!");
        } else {
            loginAndPass[2] = enterName;
        }

        return loginAndPass;
    }
}