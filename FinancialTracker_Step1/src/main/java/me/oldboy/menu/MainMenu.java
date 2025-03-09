package me.oldboy.menu;

import me.oldboy.controller.*;
import me.oldboy.entity.feature.Role;
import me.oldboy.menu.submenu.*;
import me.oldboy.service.UserService;

import java.util.Scanner;

/**
 * The main menu of the program for interaction with the user.
 *
 */
public class MainMenu {

    private static MainMenu INSTANCE;

    public static MainMenu getInstance() {
        if(INSTANCE == null){
            INSTANCE = new MainMenu();
        }
        return INSTANCE;
    }

    private static UserService userService = UserService.getInstance();
    private static EditAccountMenu editAccountMenu = new EditAccountMenu(UserController.getInstance());
    private static TransactionMenu transactionMenu = new TransactionMenu(TransactionController.getInstance());
    private static BudgetMenu budgetMenu = new BudgetMenu(BudgetController.getInstance());
    private static GoalsMenu goalsMenu = new GoalsMenu(GoalController.getInstance());
    private static StatisticMenu statisticMenu = new StatisticMenu(StatisticsController.getInstance());
    private static RegAndLoginMenu regAndLogin = new RegAndLoginMenu(AuthController.getInstance());
    private static AdminMenu adminMenu = new AdminMenu();

    public static void startMainMenu(Scanner scanner){
        Boolean repeatMenu = true;
        System.out.println("*** Добро пожаловать в систему финансового здоровья ***\n");
        System.out.println("---------------------------------------------------------------------");

        Long userId = regAndLogin.regAndLogin(scanner);
        Role userRole = userService.getUserRole(userId);
        if(userId == null){
            repeatMenu = false;
        } else {
            repeatMenu = true;
        }

        while (repeatMenu) {
            if(userRole.equals(Role.USER)){
                repeatMenu = userMenu(userId, scanner);
            } else {
                repeatMenu = adminMenu(userId, scanner);
            }
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Всего хорошего, ждем вас снова!");
        scanner.close();
    }

    private static boolean userMenu(Long userId, Scanner scanner){
        boolean repeatMenu = true;
        String userMenu = "\nВыберите интересующий вас пункт меню: \n" +
                "1 - Управление своим аккаунтом;\n" +
                "2 - Управление своими финансами; \n" +
                "3 - Управление бюджетом; \n" +
                "4 - Управление финансовыми целями; \n" +
                "5 - Финансовая статистика аккаунта; \n" +
                "6 - Покинуть программу;\n\n" +
                "Сделайте выбор и нажмите ввод: ";
        System.out.print(userMenu);

        String command = scanner.nextLine().trim();

        switch (command) {
            case "1":
                editAccountMenu.editAccountData(userId, scanner);
                break;
            case "2":
                transactionMenu.viewTransactionData(userId, scanner);
                break;
            case "3":
                budgetMenu.viewGoalInformation(userId, scanner);
                break;
            case "4":
                goalsMenu.viewGoalInformation(userId, scanner);
                break;
            case "5":
                statisticMenu.viewGoalInformation(userId, scanner);
                break;
            case "6":
                repeatMenu = false;
                break;
        }
        return repeatMenu;
    }

    private static boolean adminMenu(Long userId, Scanner scanner){
        boolean repeatMenu = true;
        String userMenu = "\nМеню администратора: \n" +
                "1 - Меню собственного аккаунту;\n" +
                "2 - Меню управление аккаунтами пользователей; \n" +
                "3 - Покинуть программу;\n\n" +
                "Сделайте выбор и нажмите ввод: ";
        System.out.print(userMenu);

        String command = scanner.nextLine().trim();

        switch (command) {
            case "1":
                userMenu(userId, scanner);
                break;
            case "2":
                adminMenu.viewAdminMenu(scanner);
                break;
            case "3":
                repeatMenu = false;
                break;
        }
        return repeatMenu;
    }
}