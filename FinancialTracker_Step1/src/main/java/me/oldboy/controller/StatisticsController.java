package me.oldboy.controller;

import me.oldboy.entity.feature.Category;
import me.oldboy.service.TransactionService;

import java.time.LocalDate;

/**
 * Controller class that serves as an intermediary between the application's view and various services.
 * Handles financial statistic request from user: current balance calculate, full statement etc.
 */
public class StatisticsController {

    private static StatisticsController INSTANCE;

    public static StatisticsController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new StatisticsController();
        }
        return INSTANCE;
    }

    private TransactionService transactionService = TransactionService.getInstance();

    public Double currentBalanceCalculate(Long userId){
        Double currentBalance = transactionService.currentBalance(userId);
        System.out.println("*** Величина текущего баланса: " + currentBalance + " ***");
        return currentBalance;
    }

    public Double totalIncomeOfPeriod(Long userId, LocalDate startDay, LocalDate finishDay){
        Double currentBalance = transactionService.calculateIncomeOfPeriod(userId, startDay, finishDay);
        System.out.println("*** Выбранный период с " + startDay + " по " + finishDay + " ***");
        System.out.println("*** Величина суммарного дохода : " + currentBalance + " ***");
        return currentBalance;
    }

    public Double totalConsumptionOfPeriod(Long userId, LocalDate startDay, LocalDate finishDay){
        Double currentBalance = transactionService.calculateConsumptionOfPeriod(userId, startDay, finishDay);
        System.out.println("*** Выбранный период с " + startDay + " по " + finishDay + " ***");
        System.out.println("*** Величина суммарных расходов : " + currentBalance + " ***");
        return currentBalance;
    }

    public void fullFinancialStatement(Long userId){
        System.out.println("*** Полный финансовый отчет ***");
        currentBalanceCalculate(userId);
        System.out.println("---------------------------------------------------------------");
        viewIncomeByCategory(userId);
        System.out.println("---------------------------------------------------------------");
        viewConsumptionByCategory(userId);
        System.out.println("---------------------------------------------------------------");
    }

    public Double[] viewConsumptionByCategory(Long userId){
        Double[] consumptionByCategory = new Double[5];
        System.out.println("*** Анализ расходов по категориям ***");

        Double buy = transactionService.totalConsumptionByCategory(userId, Category.BUY);
        consumptionByCategory[0] = buy;
        System.out.println("1. Общие затраты на покупки: " + buy);

        Double tax = transactionService.totalConsumptionByCategory(userId, Category.TAX);
        consumptionByCategory[1] = tax;
        System.out.println("2. Налоговые отчисления: " + tax);

        Double insurance = transactionService.totalConsumptionByCategory(userId, Category.INSURANCE);
        consumptionByCategory[2] = insurance;
        System.out.println("3. Оплата страховки: " + insurance);

        Double debt = transactionService.totalConsumptionByCategory(userId, Category.DEBT);
        consumptionByCategory[3] = debt;
        System.out.println("4. Возврат долгов: " + debt);

        Double investments = transactionService.totalConsumptionByCategory(userId, Category.INVESTMENTS);
        consumptionByCategory[4] = investments;
        System.out.println("5. Отчисления в инвестиционные проекты: " + investments);

        return consumptionByCategory;
    }

    private Double[] viewIncomeByCategory(Long userId){
        Double[] incomeByCategory = new Double[4];
        System.out.println("*** Анализ доходов по категориям ***");

        Double salary = transactionService.totalConsumptionByCategory(userId, Category.SALARY);
        incomeByCategory[0] = salary;
        System.out.println("1. Зарплата: " + salary);

        Double royalties = transactionService.totalConsumptionByCategory(userId, Category.ROYALTIES);
        incomeByCategory[1] = royalties;
        System.out.println("2. Авторские и лицензионные отчисления: " + royalties);

        Double sale = transactionService.totalConsumptionByCategory(userId, Category.SALE);
        incomeByCategory[2] = sale;
        System.out.println("3. Прочие продажи: " + sale);

        Double investments = transactionService.totalConsumptionByCategory(userId, Category.INVESTMENTS);
        incomeByCategory[3] = investments;
        System.out.println("4. Доходы от инвестиций: " + investments);

        return incomeByCategory;
    }
}
