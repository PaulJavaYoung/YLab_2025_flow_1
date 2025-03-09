package me.oldboy.data_base;

import me.oldboy.entity.Budget;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BudgetDBTest {
    public BudgetDB budgetDB;

    @BeforeEach
    public void initBase(){
        budgetDB = new BudgetDB();
    }

    @AfterEach
    public void killBase(){
        budgetDB.getBudgetsDb().clear();
    }

    @Test
    void shouldReturnCurrentBudgetCountTest() {
        Budget budget = new Budget();
        Budget budget_2 = new Budget();
        budgetDB.getBudgetsDb().add(budget);
        budgetDB.getBudgetsDb().add(budget_2);

        assertThat(budgetDB.getCurrentIdCount()).isEqualTo(2);
    }

    @Test
    void shouldReturnCurrentBudgetContainInDB() {
        Budget budget = Budget.builder().maximumFlow(200.0).build();
        Budget budget_2 = Budget.builder().maximumFlow(250.0).build();
        budgetDB.getBudgetsDb().add(budget);
        budgetDB.getBudgetsDb().add(budget_2);

        assertThat(budget.getMaximumFlow()).isEqualTo(budgetDB.getBudgetsDb().get(0).getMaximumFlow());
        assertThat(budget_2.getMaximumFlow()).isEqualTo(budgetDB.getBudgetsDb().get(1).getMaximumFlow());
    }

    @Test
    void shouldReturnInitBudgetContainInDB() {
        BudgetDB budgetDB_2 = BudgetDB.getInstance();

        assertThat(budgetDB_2.getCurrentIdCount()).isEqualTo(2);
    }
}