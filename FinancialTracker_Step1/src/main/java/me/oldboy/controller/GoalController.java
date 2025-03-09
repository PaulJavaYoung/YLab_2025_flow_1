package me.oldboy.controller;

import me.oldboy.entity.FinGoal;
import me.oldboy.entity.Transaction;
import me.oldboy.exception.UnexpectedEnteredData;
import me.oldboy.dao.GoalDao;
import me.oldboy.dao.TransactionDao;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controller class that serves as an intermediary between the application's view and various services.
 * Handles financial goal setting from user: create, delete, check goal status.
 */
public class GoalController {
    private static GoalController INSTANCE;

    public static GoalController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new GoalController();
        }
        return INSTANCE;
    }

    private GoalDao goalDao = GoalDao.getInstance();
    private TransactionDao transactionDao = TransactionDao.getInstance();

    public Long create(Long userId, Scanner scanner){
        System.out.println("*** Создать новую финансовую цель ***");
        String[] forCreateGoalData = createMenu(scanner);

        FinGoal newGoal = FinGoal.builder()
                .finGoalVolume(Double.parseDouble(forCreateGoalData[0]))
                .goalDescription(forCreateGoalData[1])
                .build();

        return setFinGoal(newGoal);
    }

    public boolean delete(Long userId, Scanner scanner){
        System.out.println("\n*** Удалить достигнутую финансовую цель (просмотрите список и выберите ID) *** \n");
        goalDao.findGoalsByUserId(userId).forEach(System.out::println);

        System.out.println("\nВведите выбранный ID:");
        String goalForDelete = scanner.nextLine().trim();
        Optional<FinGoal> mayBeGoal = goalDao.findById(Long.parseLong(goalForDelete));
        if(mayBeGoal.isEmpty()){
            throw new UnexpectedEnteredData("Incorrectly selected goal ID");
        } else if(!mayBeGoal.get().getUserId().equals(userId)){
            throw new UnexpectedEnteredData("Attempt to delete not yours goal");
        }

        return goalDao.delete(Long.parseLong(goalForDelete));
    }

    public void goalStatus(Long userId){
        System.out.println("\n*** Просмотр состояния целей *** \n");
        Double currentBalance =
                transactionDao.findTransactionByUserId(userId).stream()
                                                              .map(Transaction::getFinOperation)
                                                              .reduce(Double::sum)
                                                              .get();
        List<FinGoal> currentGoalsList = goalDao.findGoalsByUserId(userId);
        System.out.println("Цели достигнуты и могут быть реализованы:");
        currentGoalsList.stream()
                        .filter(goal -> goal.getFinGoalVolume() >= currentBalance)
                        .forEach(System.out::println);
    }

    private Long setFinGoal(FinGoal goal){
        return goalDao.save(goal).getGoalId();
    }

    private String[] createMenu(Scanner scanner){
        String[] createNewGoal = new String[2];

        System.out.print("Сумма необходимых финансовых затрат (цель): ");
        String enterMoneyVolume = scanner.nextLine().trim();
        createNewGoal[0] = enterMoneyVolume;

        System.out.print("Описание затрат (на что деньги нужны): ");
        String description = scanner.nextLine().trim();
        createNewGoal[1] = description;

        return createNewGoal;
    }
}
