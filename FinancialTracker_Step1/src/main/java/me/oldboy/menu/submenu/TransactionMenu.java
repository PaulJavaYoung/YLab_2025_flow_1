package me.oldboy.menu.submenu;

import lombok.RequiredArgsConstructor;
import me.oldboy.controller.TransactionController;

import java.util.Scanner;

@RequiredArgsConstructor
public class TransactionMenu {

    private final TransactionController transactionController;

    public void viewTransactionData(Long userId, Scanner scanner){
        Boolean isEntering = true;
        System.out.println("\n *** Раздел работы с транзакциями *** \n");
        do{
            System.out.print("Выберите один из пунктов меню: " +
                    "\n1 - создать транзакцию;" +
                    "\n2 - отредактировать транзакцию;" +
                    "\n3 - удалить транзакцию;" +
                    "\n4 - просмотреть транзакции в режима фильтра;" +
                    "\n5 - покинуть раздел;\n\n" +
                    "Сделайте выбор и нажмите ввод: ");
            String choiceMenuItem = scanner.nextLine();

            switch (choiceMenuItem) {
                case "1":
                    transactionController.create(userId, scanner);
                    break;
                case "2":
                    transactionController.edit(userId, scanner);
                    break;
                case "3":
                    transactionController.delete(userId, scanner);
                    break;
                case "4":
                    transactionController.viewTransactionByParameterMenu(userId, scanner);
                    break;
                case "5":
                    isEntering = false;
                    break;
            }
        } while (isEntering);
    }
}