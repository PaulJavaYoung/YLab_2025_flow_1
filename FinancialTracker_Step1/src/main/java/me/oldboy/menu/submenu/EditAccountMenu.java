package me.oldboy.menu.submenu;

import lombok.RequiredArgsConstructor;
import me.oldboy.controller.UserController;

import java.util.Scanner;

@RequiredArgsConstructor
public class EditAccountMenu {

    private final UserController userController;

    public void editAccountData(Long userId, Scanner scanner){
        Boolean isEntering = true;
        System.out.println("\n *** Раздел редактирования данных *** \n");
        do{
            System.out.print("\n Выберите один из пунктов меню: " +
                    "\n1 - редактировать данные аккаунта;" +
                    "\n2 - удалить аккаунт;" +
                    "\n3 - покинуть раздел;\n\n" +
                    "Сделайте выбор и нажмите ввод: ");
            String choiceMenuItem = scanner.nextLine();

            switch (choiceMenuItem) {
                case "1":
                    userController.update(userId, scanner);
                    break;
                case "2":
                    isEntering = userController.setDeleteStatus(userId, scanner);
                    break;
                case "3":
                    isEntering = false;
                    break;
            }
        } while (isEntering);
    }
}