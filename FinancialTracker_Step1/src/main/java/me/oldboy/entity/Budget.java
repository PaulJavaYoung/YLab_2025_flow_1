package me.oldboy.entity;

import lombok.*;

import java.util.Objects;

/**
 * Represents a monthly budget with information such as a unique id, userId, and financial flow.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Budget {
    private Long budgetId;
    private Long userId;
    private Double maximumFlow;

    public Budget(Long userId, Double maximumFlow) {
        this.userId = userId;
        this.maximumFlow = maximumFlow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return Objects.equals(budgetId, budget.budgetId) && Objects.equals(userId, budget.userId) && Objects.equals(maximumFlow, budget.maximumFlow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, userId, maximumFlow);
    }

    @Override
    public String toString() {
        return "Budget {" +
                "budgetId = " + budgetId +
                ", userId = " + userId +
                ", maximumFlow = " + maximumFlow +
                '}';
    }
}
