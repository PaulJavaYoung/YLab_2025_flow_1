package me.oldboy.menu.submenu;

import lombok.RequiredArgsConstructor;
import me.oldboy.controller.AuthController;

import java.util.Scanner;

@RequiredArgsConstructor
public class RegAndLoginMenu {

    private final AuthController authController;
    private Long userId;

    public Long regAndLogin(Scanner scanner){
        Boolean isEntering = true;
        System.out.println("*** Пожалуйста зарегистрируйтесь если вы впервые у нас ***");
        do{
            System.out.print("\nВыберите один из пунктов меню: " +
                    "\n1 - регистрация;" +
                    "\n2 - вход в систему;" +
                    "\n3 - покинуть программу;\n\n" +
                    "Сделайте выбор и нажмите ввод: ");
            String choiceMenuItem = scanner.nextLine();

            switch (choiceMenuItem) {
                case "1":
                    authController.regUser(scanner);
                    break;
                case "2":
                    userId = authController.login(scanner).getUserId();
                    isEntering = false;
                    break;
                case "3":
                    userId = null;
                    isEntering = false;
                    break;
            }
        } while (isEntering);
        return userId;
    }
}