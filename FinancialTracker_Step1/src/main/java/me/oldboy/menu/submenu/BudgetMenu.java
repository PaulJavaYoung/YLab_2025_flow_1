package me.oldboy.menu.submenu;

import lombok.RequiredArgsConstructor;
import me.oldboy.controller.BudgetController;

import java.util.Scanner;

@RequiredArgsConstructor
public class BudgetMenu {
    private final BudgetController budgetController;

    public void viewGoalInformation(Long userId, Scanner scanner){
        Boolean isEntering = true;
        System.out.println("\n *** Раздел управления бюджетом *** \n");
        do{
            System.out.print("Выберите один из пунктов меню: " +
                    "\n1 - установить месячный бюджет;" +
                    "\n2 - состояние текущего бюджета;" +
                    "\n3 - покинуть раздел;\n\n" +
                    "Сделайте выбор и нажмите ввод: ");
            String choiceMenuItem = scanner.nextLine();

            switch (choiceMenuItem) {
                case "1":
                    budgetController.createOrUpdate(userId, scanner);
                    break;
                case "2":
                    budgetController.budgetTracker(userId);
                    break;
                case "3":
                    isEntering = false;
                    break;
            }
        } while (isEntering);
    }
}