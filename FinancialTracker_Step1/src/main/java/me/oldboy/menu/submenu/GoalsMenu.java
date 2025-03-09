package me.oldboy.menu.submenu;

import lombok.RequiredArgsConstructor;
import me.oldboy.controller.GoalController;

import java.util.Scanner;

@RequiredArgsConstructor
public class GoalsMenu {

    private final GoalController goalController;

    public void viewGoalInformation(Long userId, Scanner scanner){
        Boolean isEntering = true;
        System.out.println("\n *** Раздел работы с финансовыми целями *** \n");
        do{
            System.out.print("Выберите один из пунктов меню: " +
                    "\n1 - создать цель;" +
                    "\n2 - текущее состояние целей;" +
                    "\n3 - удалить цель;" +
                    "\n4 - покинуть раздел;\n\n" +
                    "Сделайте выбор и нажмите ввод: ");
            String choiceMenuItem = scanner.nextLine();

            switch (choiceMenuItem) {
                case "1":
                    goalController.create(userId, scanner);
                    break;
                case "2":
                    goalController.goalStatus(userId);
                    break;
                case "3":
                    goalController.delete(userId, scanner);
                    break;
                case "4":
                    isEntering = false;
                    break;
            }
        } while (isEntering);
    }
}