package me.oldboy.controller;

import me.oldboy.dao.TransactionDao;
import me.oldboy.dao.UserDao;
import me.oldboy.entity.Transaction;
import me.oldboy.entity.User;
import me.oldboy.exception.UnexpectedEnteredData;

import java.util.List;
import java.util.Scanner;

/**
 * Controller class that serves as an intermediary between the application's view and various services.
 * Handles administrator operation delete or block user, view users list and user transaction list.
 */
public class AdminController {
    private static AdminController INSTANCE;

    public static AdminController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new AdminController();
        }
        return INSTANCE;
    }

    private UserDao userDao = UserDao.getInstance();
    private TransactionDao transactionDao = TransactionDao.getInstance();

    public void viewUserState(Scanner scanner){
        System.out.println("*** Раздел просмотра пользователей и их транзакций ***");
        selectUserAndTransactionMenu(scanner);
    }

    private void selectUserAndTransactionMenu(Scanner scanner){
        Boolean isEntering = true;
        do{
            System.out.print("Выберите один из пунктов меню: " +
                    "\n1 - просмотреть список пользователей;" +
                    "\n2 - просмотреть транзакции выбранного пользователя;" +
                    "\n3 - заблокировать или удалить выбранного пользователя;" +
                    "\n4 - покинуть раздел;\n\n" +
                    "Сделайте выбор и нажмите ввод: ");
            String choiceMenuItem = scanner.nextLine();

            switch (choiceMenuItem) {
                case "1":
                    viewAllUser().forEach(System.out::println);
                    break;
                case "2":
                    viewUserTransaction(scanner).forEach(System.out::println);
                    break;
                case "3":
                    deleteOrBlockedMenu(scanner);
                    break;
                case "4":
                    isEntering = false;
                    break;
            }
        } while (isEntering);
    }

    private boolean deleteOrBlockedMenu(Scanner scanner){
        System.out.println("Удалить или заблокировать пользователя (remove/block): ");
        String enterAnswer = scanner.nextLine().trim().toLowerCase();
        System.out.println("Укажите ID пользователя для выбранной операции: ");
        String userId = scanner.nextLine().trim();
        if(enterAnswer.equals("remove")){
            return userDao.delete(Long.parseLong(userId));
        } else if(enterAnswer.equals("block")){
            return userDao.setBlockedStatus(Long.parseLong(userId));
        } else {
            throw new UnexpectedEnteredData("Select operation and user ID");
        }
    }

    private List<User> viewAllUser(){
        return userDao.findAll();
    }

    private List<Transaction> viewUserTransaction(Scanner scanner){
        System.out.println("Укажите ID пользователя для просмотра его транзакций: ");
        String userId = scanner.nextLine().trim();
        return transactionDao.findTransactionByUserId(Long.parseLong(userId));
    }
}
