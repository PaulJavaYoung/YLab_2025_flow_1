package me.oldboy.menu.submenu;

import lombok.RequiredArgsConstructor;
import me.oldboy.controller.StatisticsController;
import me.oldboy.exception.UnexpectedEnteredData;

import java.time.LocalDate;
import java.util.Scanner;

@RequiredArgsConstructor
public class StatisticMenu {

    private final StatisticsController statisticsController;

    public void viewGoalInformation(Long userId, Scanner scanner){
        Boolean isEntering = true;
        System.out.println("\n *** Аналитико-статистический раздел *** \n");
        do{
            System.out.print("Выберите один из пунктов меню: " +
                    "\n1 - посмотреть размер текущего баланса;" +
                    "\n2 - рассчитать суммарный расход/доход за выбранный период;" +
                    "\n3 - анализ расходов по категориям;" +
                    "\n4 - посмотреть полный отчет;" +
                    "\n5 - покинуть раздел;\n\n" +
                    "Сделайте выбор и нажмите ввод: ");
            String choiceMenuItem = scanner.nextLine();

            switch (choiceMenuItem) {
                case "1":
                    statisticsController.currentBalanceCalculate(userId);
                    break;
                case "2":
                    choicePeriodMenu(userId, scanner);
                    break;
                case "3":
                    statisticsController.viewConsumptionByCategory(userId);
                    break;
                case "4":
                    statisticsController.fullFinancialStatement(userId);
                    break;
                case "5":
                    isEntering = false;
                    break;
            }
        } while (isEntering);
    }

    private void choicePeriodMenu(Long userId, Scanner scanner){
        System.out.print("Введите дату начала периода (yyyy-mm-dd): ");
        String firstDate = scanner.nextLine();
        LocalDate startDate = LocalDate.parse(firstDate);

        System.out.print("Введите дату окончания периода (yyyy-mm-dd): ");
        String lastDate = scanner.nextLine();
        LocalDate finishDate = LocalDate.parse(lastDate);

        if(startDate.isAfter(finishDate)){
            throw new UnexpectedEnteredData("Invalid range specified");
        }
        System.out.println("---------------------------------------------------------------");
        statisticsController.totalConsumptionOfPeriod(userId, startDate, finishDate);
        System.out.println("---------------------------------------------------------------");
        statisticsController.totalIncomeOfPeriod(userId, startDate, finishDate);
        System.out.println("---------------------------------------------------------------");
    }
}