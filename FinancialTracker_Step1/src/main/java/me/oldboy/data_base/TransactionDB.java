package me.oldboy.data_base;

import lombok.Getter;
import lombok.Setter;
import me.oldboy.entity.Transaction;
import me.oldboy.entity.feature.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Transaction database imitation.
 */
public class TransactionDB {

    private static TransactionDB INSTANCE;

    public static TransactionDB getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TransactionDB();
            initialSetup();
        }
        return INSTANCE;
    }

    @Setter
    private Long transactionIdCount;

    @Getter
    private List<Transaction> transactionsDb = new ArrayList<>();

    public Long getCurrentIdCount() {
        this.transactionIdCount = Long.valueOf(transactionsDb.size());
        return transactionIdCount;
    }

    private static void initialSetup(){
        /* USER Transaction */
        Transaction transaction_1 = Transaction.builder()
                .transactionId(1L)
                .userId(2L)
                .finOperation(+200.0)
                .finCategory(Category.SALARY)
                .date(LocalDate.parse("2025-08-11"))
                .description("Зарплата в ООО'Рога и Копыта'")
                .build();

        Transaction transaction_2 = Transaction.builder()
                .transactionId(2L)
                .userId(2L)
                .finOperation(+800.0)
                .finCategory(Category.ROYALTIES)
                .date(LocalDate.parse("2025-08-03"))
                .description("Отчисления за книгу 'Вертикальный предел'")
                .build();

        Transaction transaction_3 = Transaction.builder()
                .transactionId(3L)
                .userId(2L)
                .finOperation(-400.0)
                .finCategory(Category.BUY)
                .date(LocalDate.parse("2025-08-23"))
                .description("Оплата квартиры")
                .build();

        Transaction transaction_4 = Transaction.builder()
                .transactionId(4L)
                .userId(2L)
                .finOperation(+150.0)
                .finCategory(Category.SALE)
                .date(LocalDate.parse("2025-07-21"))
                .description("Продажа горного велосипеда")
                .build();

        Transaction transaction_5 = Transaction.builder()
                .transactionId(5L)
                .userId(2L)
                .finOperation(-30.0)
                .finCategory(Category.TAX)
                .date(LocalDate.parse("2025-07-13"))
                .description("Налог с продажи горного велосипеда")
                .build();

        Transaction transaction_6 = Transaction.builder()
                .transactionId(6L)
                .userId(2L)
                .finOperation(-40.0)
                .finCategory(Category.INSURANCE)
                .date(LocalDate.parse("2025-07-03"))
                .description("Страховка в ОАО'Не лезь на рожон'")
                .build();

        Transaction transaction_7 = Transaction.builder()
                .transactionId(7L)
                .userId(2L)
                .finOperation(-80.0)
                .finCategory(Category.INVESTMENTS)
                .date(LocalDate.parse("2025-06-23"))
                .description("Покупка облигаций АО'Паритет'")
                .build();

        Transaction transaction_8 = Transaction.builder()
                .transactionId(8L)
                .userId(2L)
                .finOperation(+280.0)
                .finCategory(Category.ROYALTIES)
                .date(LocalDate.parse("2025-06-09"))
                .description("Отчисления по лицензии N3424345")
                .build();

        /* ADMIN Transaction */
        Transaction transaction_9 = Transaction.builder()
                .transactionId(9L)
                .userId(1L)
                .finOperation(-400.0)
                .finCategory(Category.INSURANCE)
                .date(LocalDate.parse("2025-03-03"))
                .description("Страховка в ОАО'Всегда и всюду'")
                .build();

        Transaction transaction_10 = Transaction.builder()
                .transactionId(10L)
                .userId(1L)
                .finOperation(-180.0)
                .finCategory(Category.INVESTMENTS)
                .date(LocalDate.parse("2025-03-23"))
                .description("Покупка акций АО'Вам и не снилось'")
                .build();

        Transaction transaction_11 = Transaction.builder()
                .transactionId(11L)
                .userId(1L)
                .finOperation(+650.0)
                .finCategory(Category.ROYALTIES)
                .date(LocalDate.parse("2025-03-09"))
                .description("Отчисления по патенту ПТ34-12-У")
                .build();

        INSTANCE.getTransactionsDb().add(transaction_1);
        INSTANCE.getTransactionsDb().add(transaction_2);
        INSTANCE.getTransactionsDb().add(transaction_3);
        INSTANCE.getTransactionsDb().add(transaction_4);
        INSTANCE.getTransactionsDb().add(transaction_5);
        INSTANCE.getTransactionsDb().add(transaction_6);
        INSTANCE.getTransactionsDb().add(transaction_7);
        INSTANCE.getTransactionsDb().add(transaction_8);
        INSTANCE.getTransactionsDb().add(transaction_9);
        INSTANCE.getTransactionsDb().add(transaction_10);
        INSTANCE.getTransactionsDb().add(transaction_11);
    }
}