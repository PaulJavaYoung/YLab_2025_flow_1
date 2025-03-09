package me.oldboy.data_base;

import lombok.Getter;
import lombok.Setter;
import me.oldboy.entity.Budget;

import java.util.ArrayList;
import java.util.List;

/**
 * Budget database imitation.
 */
public class BudgetDB {

    private static BudgetDB INSTANCE;

    public static BudgetDB getInstance() {
        if(INSTANCE == null){
            INSTANCE = new BudgetDB();
            initialSetup();
        }
        return INSTANCE;
    }

    @Setter
    private Long budgetIdCount;

    @Getter
    private List<Budget> budgetsDb = new ArrayList<>();

    public Long getCurrentIdCount() {
        this.budgetIdCount = Long.valueOf(budgetsDb.size());
        return budgetIdCount;
    }

    private static void initialSetup(){
        Budget budget_1 = Budget.builder()
                .budgetId(1L)
                .userId(1L)
                .maximumFlow(300.0)
                .build();

        Budget budget_2 = Budget.builder()
                .budgetId(2L)
                .userId(2L)
                .maximumFlow(400.0)
                .build();

        INSTANCE.getBudgetsDb().add(budget_1);
        INSTANCE.getBudgetsDb().add(budget_2);
    }
}
