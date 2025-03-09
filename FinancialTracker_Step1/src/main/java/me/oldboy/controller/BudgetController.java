package me.oldboy.controller;

import me.oldboy.entity.Budget;
import me.oldboy.entity.Transaction;
import me.oldboy.dao.BudgetDao;
import me.oldboy.dao.TransactionDao;
import me.oldboy.exception.BudgetControllerException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controller class that serves as an intermediary between the application's view and various services.
 * Handles budget create, update, track exceed etc.
 */
public class BudgetController {
    private static BudgetController INSTANCE;

    public static BudgetController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new BudgetController();
        }
        return INSTANCE;
    }

    private BudgetDao budgetDao = BudgetDao.getInstance();
    private TransactionDao transactionDao = TransactionDao.getInstance();

    public Long createOrUpdate(Long userId, Scanner scanner){
        Budget budget = null;
        System.out.println("*** Установить месячный бюджет ***");
        Optional<Budget> mayByBudget = budgetDao.findBudgetByUserId(userId);
        if (mayByBudget.isEmpty()) {
            budget = createUpdateMenu(userId, scanner);
            return budgetDao.save(budget).getBudgetId();
        } else {
            budget = createUpdateMenu(userId, scanner);
            budgetDao.update(budget);
            return mayByBudget.get().getBudgetId();
        }
    }

    public boolean budgetTracker(Long userId){
        boolean isBudgetExceeded = false;
        System.out.println("\n*** Просмотр бюджета текущего месяца *** \n");
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        List<Transaction> selectedTransactions = transactionDao.findTransactionByUserId(userId);
        Optional<Budget> budget = budgetDao.findBudgetByUserId(userId);
        Double currentMonthBalance = selectedTransactions.stream()
                .filter(transaction -> (transaction.getDate().getMonthValue() == currentMonth &&
                                        transaction.getDate().getYear() == currentYear))
                .map(Transaction::getFinOperation)
                .reduce((x, y) -> x + y).get();

        if(selectedTransactions.size() != 0) {
            if(budget.isEmpty()){
                throw new BudgetControllerException("Current budget size is not set, please 'just do it'");
            }
            if (currentMonthBalance < budget.get().getMaximumFlow()) {
                System.out.println("Внимание! Превышен месячный бюджет.");
                System.out.println("Баланс текущего месяца: " + currentMonthBalance);
                System.out.println("Установленный бюджет: " + budget);
                isBudgetExceeded = true;
            }
        } else {
            System.out.println("В этом месяце у вас не было ни одной транзакции.");
            System.out.println("Баланс текущего месяца: " + currentMonthBalance);
            System.out.println("Установленный бюджет: " + budget);
        }
        return isBudgetExceeded;
    }

    private Budget createUpdateMenu(Long userId, Scanner scanner){
        System.out.print("Введите величину месячного бюджета: ");
        String enterMoneyVolume = scanner.nextLine().trim();

        return Budget.builder()
                     .userId(userId)
                     .maximumFlow(Double.parseDouble(enterMoneyVolume))
                     .build();
    }
}