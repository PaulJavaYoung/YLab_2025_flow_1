package me.oldboy.controller;

import me.oldboy.entity.Transaction;
import me.oldboy.entity.feature.Category;
import me.oldboy.exception.UnexpectedEnteredData;
import me.oldboy.dao.TransactionDao;
import me.oldboy.service.TransactionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Controller class that serves as an intermediary between the application's view and various services.
 * Handles transaction create, update, delete operation, viewTransactionByParameter.
 */
public class TransactionController {

    private static TransactionController INSTANCE;

    public static TransactionController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new TransactionController();
        }
        return INSTANCE;
    }

    private TransactionDao transactionDao = TransactionDao.getInstance();
    private TransactionService transactionService = TransactionService.getInstance();

    public Transaction create(Long userId, Scanner scanner){
        System.out.println("*** Создать новую транзакцию ***");

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        if(transactionService.isMonthBalanceExcess(userId, currentYear, currentMonth)){
            Double excess = transactionService.excessAmount(userId, currentYear, currentMonth);
            System.out.println("Бюджет текущего месяца уже превышен на: " + excess);
        }

        String[] forCreateTransactionData = createMenu(scanner);

        Transaction createdTransaction = Transaction.builder()
                                                    .userId(userId)
                                                    .finOperation(Double.valueOf(forCreateTransactionData[0]))
                                                    .finCategory(Category.valueOf(forCreateTransactionData[1]))
                                                    .date(LocalDate.parse(forCreateTransactionData[2]))
                                                    .description(forCreateTransactionData[3])
                                                    .build();

        return transactionDao.save(createdTransaction);
    }

    public void edit(Long userId, Scanner scanner){
        System.out.println("\n*** Редактировать существующую транзакцию (просмотрите список и выберите ID) *** \n");
        transactionDao.findTransactionByUserId(userId)
                          .forEach(transaction -> System.out.println(transaction));

        System.out.println("\nВведите выбранный ID:");
        String transactionIdForEdit = scanner.nextLine().trim();
        Optional<Transaction> mayBeTransaction =
                transactionDao.findById(Long.parseLong(transactionIdForEdit));
        if(mayBeTransaction.isEmpty()){
            throw new UnexpectedEnteredData("Incorrectly selected ID");
        } else if(!mayBeTransaction.get().getUserId().equals(userId)){
            throw new UnexpectedEnteredData("Attempt to edit not yours transaction");
        }
        System.out.println("Вы начинаете редактировать транзакцию с ID = '" + transactionIdForEdit + "'");

        String[] forEditData = editMenu(userId, scanner);

        Transaction editedTransaction = Transaction.builder()
                .transactionId(Long.valueOf(transactionIdForEdit))
                .userId(userId)
                .finOperation(Double.valueOf(forEditData[0]))
                .finCategory(Category.valueOf(forEditData[1]))
                .date(mayBeTransaction.get().getDate())
                .description(forEditData[2])
                .build();

        transactionDao.save(editedTransaction);
    }

    public boolean delete(Long userId, Scanner scanner){
        System.out.println("\n*** Удалить существующую транзакцию (просмотрите список и выберите ID) *** \n");
        transactionDao.findTransactionByUserId(userId)
                .forEach(transaction -> System.out.println(transaction));

        System.out.println("\nВведите выбранный ID:");
        String transactionIdForEdit = scanner.nextLine().trim();
        Optional<Transaction> mayBeTransaction =
                transactionDao.findById(Long.parseLong(transactionIdForEdit));
        if(mayBeTransaction.isEmpty()){
            throw new UnexpectedEnteredData("Incorrectly selected ID");
        } else if(!mayBeTransaction.get().getUserId().equals(userId)){
            throw new UnexpectedEnteredData("Attempt to delete not yours transaction");
        }

        return transactionDao.delete(Long.parseLong(transactionIdForEdit));
    }

    public void viewTransactionByParameterMenu(Long userId, Scanner scanner){
        Boolean isEntering = true;
        System.out.println("\n *** Раздел просмотра транзакций *** \n");
        do{
            System.out.print("Выберите один из пунктов меню: " +
                    "\n1 - просмотреть все транзакции с выбранной датой;" +
                    "\n2 - просмотреть все транзакции выбранной категории;" +
                    "\n3 - просмотреть все расходные транзакции;" +
                    "\n4 - просмотреть все приходные транзакции;" +
                    "\n5 - закончить просмотр;\n\n" +
                    "Сделайте выбор и нажмите ввод: ");
            String choiceMenuItem = scanner.nextLine();

            switch (choiceMenuItem) {
                case "1":
                    viewAllTransactionByDate(userId, scanner);
                    break;
                case "2":
                    viewAllTransactionByCategory(userId, scanner);
                    break;
                case "3":
                    viewAllTransactionByExpense(userId);
                    break;
                case "4":
                    viewAllTransactionByIncome(userId);
                    break;
                case "5":
                    isEntering = false;
                    break;
            }
        } while (isEntering);
    }

    private String[] createMenu(Scanner scanner){
        String[] createNewTransaction = new String[4];

        System.out.print("Введите сумму транзакции (если это расходная операция укажите знак '-'): ");
        String enterOperation = scanner.nextLine().trim();
            createNewTransaction[0] = enterOperation;

        System.out.print("Введите категорию из возможных (SALARY, ROYALTIES, BUY, SALE, TAX, INSURANCE, DEBT, INVESTMENTS): ");
        String enterCategory = scanner.nextLine().trim();
        if(!enterCategory.matches("SALARY|ROYALTIES|BUY|SALE|TAX|INSURANCE|DEBT|INVESTMENTS")){
            throw new UnexpectedEnteredData("Check the spelling of the category");
        } else {
            createNewTransaction[1] = enterCategory;
        }

        System.out.print("Введите дату транзакции в формате (yyyy-mm-dd): ");
        String enteredDate = scanner.nextLine().trim();
        createNewTransaction[2] = enteredDate;

        System.out.print("Введите описание транзакции: ");
        String description = scanner.nextLine().trim();
        createNewTransaction[3] = description;

        return createNewTransaction;
    }

    private String[] editMenu(Long userId, Scanner scanner){
        String[] editTransaction = new String[3];

        System.out.print("Отредактируйте сумму транзакции (если это расходная операция укажите знак '-'): ");
        String enterOperation = scanner.nextLine().trim();
        editTransaction[0] = enterOperation;

        System.out.print("Отредактируйте категорию, выберите вариант (SALARY, ROYALTIES, BUY, SALE, TAX, INSURANCE, DEBT, INVESTMENTS): ");
        String enterCategory = scanner.nextLine().trim();
        if(!enterCategory.matches("SALARY|ROYALTIES|BUY|SALE|TAX|INSURANCE|DEBT|INVESTMENTS")){
            throw new UnexpectedEnteredData("Check the spelling of the category");
        } else {
            editTransaction[1] = enterCategory;
        }

        System.out.print("Измените описание транзакции: ");
        String description = scanner.nextLine().trim();
        editTransaction[2] = description;

        return editTransaction;
    }

    private void viewAllTransactionByDate(Long userId, Scanner scanner){
        System.out.print("Введите дату транзакции в формате (yyyy-mm-dd): ");
        String enteredDate = scanner.nextLine().trim();
        System.out.println("------------------------------------------------------");
        selectAllTransactionByDate(userId, LocalDate.parse(enteredDate))
                .forEach(System.out::println);
    }

    private void viewAllTransactionByCategory(Long userId, Scanner scanner){
        System.out.print("Введите категорию транзакции (SALARY, ROYALTIES, BUY, SALE, TAX, INSURANCE, DEBT, INVESTMENTS): ");
        String enteredCategory = scanner.nextLine().trim();
        System.out.println("------------------------------------------------------");
        selectAllTransactionByCategory(userId, Category.valueOf(enteredCategory))
                .forEach(System.out::println);
    }

    private void viewAllTransactionByIncome(Long userId){
        System.out.println("------------------------------------------------------");
        selectAllTransactionByIncome(userId).forEach(System.out::println);
    }

    private void viewAllTransactionByExpense(Long userId){
        System.out.println("------------------------------------------------------");
        selectAllTransactionByExpense(userId).forEach(System.out::println);
    }

    private List<Transaction> selectAllTransactionByDate(Long userId, LocalDate localDate){
        return transactionDao.findTransactionByUserId(userId).stream()
                .filter(transaction -> transaction.getDate().equals(localDate))
                .collect(Collectors.toList());
    }

    private List<Transaction> selectAllTransactionByCategory(Long userId, Category category){
        return transactionDao.findTransactionByUserId(userId).stream()
                .filter(transaction -> transaction.getFinCategory().equals(category))
                .collect(Collectors.toList());
    }

    private List<Transaction> selectAllTransactionByIncome(Long userId){
        return transactionDao.findTransactionByUserId(userId).stream()
                .filter(transaction -> transaction.getFinOperation() > 0)
                .collect(Collectors.toList());
    }

    private List<Transaction> selectAllTransactionByExpense(Long userId){
        return transactionDao.findTransactionByUserId(userId).stream()
                .filter(transaction -> transaction.getFinOperation() < 0)
                .collect(Collectors.toList());
    }
}