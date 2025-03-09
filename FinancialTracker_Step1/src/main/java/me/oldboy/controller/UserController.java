package me.oldboy.controller;

import me.oldboy.entity.User;
import me.oldboy.entity.feature.Auth;
import me.oldboy.entity.feature.Role;
import me.oldboy.exception.WrongEnteredDataFormatException;
import me.oldboy.dao.UserDao;

import java.util.Scanner;

/**
 * Controller class that serves as an intermediary between the application's view and various services.
 * Handles user update, delete operation.
 */
public class UserController {
    private static UserController INSTANCE;

    public static UserController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new UserController();
        }
        return INSTANCE;
    }

    private UserDao userDao = UserDao.getInstance();

    public void update(Long userId, Scanner scanner){
        System.out.println("\nОбновить/редактировать свои данные.");
        User forUpdateUser = userDao.findById(userId).get();

        String[] updateUserData = editMenu(forUpdateUser, scanner);

        User updatedUser = User.builder()
                .userId(userId)
                .email(updateUserData[0])
                .password(updateUserData[1])
                .userName(updateUserData[2])
                .role(Role.USER)
                .auth(Auth.IS_ACTIVE)
                .build();

        userDao.update(updatedUser);
        System.out.println("\nДанные обновлены.");
    }

    public boolean setDeleteStatus(Long userId, Scanner scanner){
        if (deleteMenu(scanner)){
            return userDao.setDeleteStatus(userId);
        } else {
            return false;
        }
    }

    private String[] editMenu(User user, Scanner scanner){
        String[] loginAndPass = new String[3];

        System.out.print("Введите новый email (для сохранения старого просто нажмите ввод): ");
        String enterEmail = scanner.nextLine().trim();
        if(enterEmail.length() == 0){
            loginAndPass[0] = user.getEmail();
        } else if(!enterEmail.matches("\\w+@\\w+.(ru|com|me)")){
            throw new WrongEnteredDataFormatException("Not email was entered!");
        } else {
            loginAndPass[0] = enterEmail;
        }

        System.out.print("Введите новый пароль (для сохранения старого просто нажмите ввод): ");
        String enterPassword = scanner.nextLine().trim();
        if(enterPassword.length() == 0){
            loginAndPass[1] = user.getPassword();
        } else if(enterPassword.matches("\\s+")){
            throw new WrongEnteredDataFormatException("Password cannot contain only spaces!");
        } else {
            loginAndPass[1] = enterPassword;
        }

        System.out.print("Введите имя (для сохранения старого просто нажмите ввод): ");
        String enterName = scanner.nextLine().trim();
        if(enterPassword.length() == 0) {
            loginAndPass[2] = user.getUserName();
        } else if(enterName.matches("\\s+")){
            throw new WrongEnteredDataFormatException("Name cannot contain only spaces!");
        } else {
            loginAndPass[2] = enterName;
        }
        return loginAndPass;
    }

    private boolean deleteMenu(Scanner scanner){
        System.out.println("Вы хотите удалить данные (yes/no):");
        String enterAnswer = scanner.nextLine().trim();
        if(enterAnswer.equals("yes")){
            return true;
        } else if(enterAnswer.equals("no")){
            return false;
        } else {
            throw new WrongEnteredDataFormatException("Expected yes or no");
        }
    }
}
