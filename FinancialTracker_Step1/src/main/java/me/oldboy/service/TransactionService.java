package me.oldboy.service;

import me.oldboy.dao.BudgetDao;
import me.oldboy.dao.TransactionDao;
import me.oldboy.entity.Budget;
import me.oldboy.entity.Transaction;
import me.oldboy.entity.feature.Category;
import me.oldboy.exception.TransactionServiceException;
import me.oldboy.exception.UnexpectedEnteredData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service for transaction-related functionality.
 *
 */
public class TransactionService {

    private static TransactionService INSTANCE;

    public static TransactionService getInstance() {
        if(INSTANCE == null){
            INSTANCE = new TransactionService();
        }
        return INSTANCE;
    }

    private TransactionDao transactionDao = TransactionDao.getInstance();
    private BudgetDao budgetDao = BudgetDao.getInstance();

    public boolean isMonthBalanceExcess(Long userId, Integer year, Integer month){
        boolean isBalanceExcess = false;

        Double selectedMonthBalance = monthBalanceCalculate(userId, year, month);
        Double userBudget = getCurrentUserBudget(userId);

        if(selectedMonthBalance < userBudget){
            isBalanceExcess = true;
        }

        return isBalanceExcess;
    }

    public Double excessAmount(Long userId, Integer year, Integer month){
        return getCurrentUserBudget(userId) - monthBalanceCalculate(userId, year, month);
    }

    public Double currentBalance(Long userId){
        return transactionDao.findTransactionByUserId(userId)
                        .stream()
                        .map(Transaction::getFinOperation)
                        .reduce((x,y) -> x+y)
                        .get();
    }

    public Double monthBalanceCalculate(Long userId, Integer year, Integer month){
        List<Transaction> monthTransaction = transactionDao.findTransactionByUserId(userId);
        if(monthTransaction.size() == 0) {
            throw new TransactionServiceException("Have no transaction in database");
        } else {
            return monthTransaction.stream()
                    .filter(transaction -> (transaction.getDate().getYear() == year &&
                                            transaction.getDate().getMonthValue() == month))
                    .map(Transaction::getFinOperation)
                    .reduce((x, y) -> x + y)
                    .get();
        }
    }

    private Double getCurrentUserBudget(Long userId){
        Optional<Budget> monthBudget = budgetDao.findBudgetByUserId(userId);
        if(monthBudget.isEmpty()){
            throw new UnexpectedEnteredData("Wrong userId or month");
        }
        return monthBudget.get().getMaximumFlow();
    }

    public Double calculateIncomeOfPeriod(Long userId, LocalDate startDay, LocalDate stopDay){
        List<Transaction> monthTransaction = transactionDao.findTransactionByUserId(userId);
        if(monthTransaction.size() == 0) {
            throw new TransactionServiceException("Have no transaction in database");
        } else {
            return monthTransaction.stream()
                    .filter(transaction -> (transaction.getDate().isAfter(startDay) &&
                                            transaction.getDate().isBefore(stopDay) &&
                                            transaction.getFinOperation() > 0))
                    .map(Transaction::getFinOperation)
                    .reduce((x, y) -> x + y)
                    .orElse(0.0);
        }
    }

    public Double calculateConsumptionOfPeriod(Long userId, LocalDate startDay, LocalDate stopDay){
        List<Transaction> monthTransaction = transactionDao.findTransactionByUserId(userId);
        if(monthTransaction.size() == 0) {
            throw new TransactionServiceException("Have no transaction in database");
        } else {
            return monthTransaction.stream()
                    .filter(transaction -> (transaction.getDate().isAfter(startDay) &&
                                            transaction.getDate().isBefore(stopDay) &&
                                            transaction.getFinOperation() < 0))
                    .map(Transaction::getFinOperation)
                    .reduce((x, y) -> x + y)
                    .orElse(0.0);
        }
    }

    public Double totalConsumptionByCategory(Long userId, Category category){
        List<Transaction> monthTransaction = transactionDao.findTransactionByUserId(userId);
        if(monthTransaction.size() == 0) {
            throw new TransactionServiceException("Have no transaction in database");
        } else {
            return monthTransaction.stream()
                    .filter(transaction -> (transaction.getFinCategory().equals(category) &&
                                            transaction.getFinOperation() < 0))
                    .map(Transaction::getFinOperation)
                    .reduce((x, y) -> x + y)
                    .orElse(0.0);
        }
    }

    public Double totalIncomeByCategory(Long userId, Category category){
        List<Transaction> monthTransaction = transactionDao.findTransactionByUserId(userId);
        if(monthTransaction.size() == 0) {
            throw new TransactionServiceException("Have no transaction in database");
        } else {
            return monthTransaction.stream()
                    .filter(transaction -> (transaction.getFinCategory().equals(category) &&
                            transaction.getFinOperation() > 0))
                    .map(Transaction::getFinOperation)
                    .reduce((x, y) -> x + y)
                    .orElse(0.0);
        }
    }
}
