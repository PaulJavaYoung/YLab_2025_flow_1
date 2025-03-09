package me.oldboy.data_base;

import me.oldboy.entity.Transaction;
import me.oldboy.entity.feature.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TransactionDBTest {

    public TransactionDB transactionDB;

    @BeforeEach
    public void initBase(){
        transactionDB = new TransactionDB();
    }

    @AfterEach
    public void killBase(){
        transactionDB.getTransactionsDb().clear();
    }

    @Test
    void shouldReturnCurrentTransactionCountTest() {
        Transaction transaction = new Transaction();
        Transaction transaction_2 = new Transaction();
        transactionDB.getTransactionsDb().add(transaction);
        transactionDB.getTransactionsDb().add(transaction_2);

        assertThat(transactionDB.getCurrentIdCount()).isEqualTo(2);
    }

    @Test
    void shouldReturnCurrentTransactionContainInDB() {
        Transaction transaction = Transaction.builder().userId(1L).finOperation(-200.0).finCategory(Category.BUY).date(LocalDate.parse("2025-03-04")).description("Покупка").build();
        Transaction transaction_2 = Transaction.builder().userId(2L).finOperation(200.0).finCategory(Category.SALE).date(LocalDate.parse("2025-02-04")).description("Продажа").build();
        transactionDB.getTransactionsDb().add(transaction);
        transactionDB.getTransactionsDb().add(transaction_2);

        assertThat(transaction.getFinCategory()).isEqualTo(transactionDB.getTransactionsDb().get(0).getFinCategory());
        assertThat(transaction_2.getFinCategory()).isEqualTo(transactionDB.getTransactionsDb().get(1).getFinCategory());
    }

    @Test
    void shouldReturnInitTransactionContainInDB() {
        TransactionDB transactionDB_1 = TransactionDB.getInstance();

        assertThat(transactionDB_1.getCurrentIdCount()).isEqualTo(11);
    }
}