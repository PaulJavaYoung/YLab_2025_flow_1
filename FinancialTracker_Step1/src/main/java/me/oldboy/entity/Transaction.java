package me.oldboy.entity;

import lombok.*;
import me.oldboy.entity.feature.Category;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a Transaction with information such as a unique id, userId, financial operation, financial category, date of transaction, and transaction description.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Transaction {
    private Long transactionId;
    private Long userId;
    private Double finOperation;
    private Category finCategory;
    private LocalDate date;
    private String description;

    public Transaction(Long userId, Double finOperation, Category finCategory, LocalDate date, String description) {
        this.userId = userId;
        this.finOperation = finOperation;
        this.finCategory = finCategory;
        this.date = date;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(userId, that.userId) && Objects.equals(finOperation, that.finOperation) && finCategory == that.finCategory && Objects.equals(date, that.date) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, userId, finOperation, finCategory, date, description);
    }

    @Override
    public String toString() {
        return "Transaction {" +
                "trnId = " + transactionId +
                ", userId = " + userId +
                ", finOp = " + finOperation +
                ", Category = " + finCategory +
                ", date = " + date +
                ", description = '" + description + '\'' +
                '}';
    }
}