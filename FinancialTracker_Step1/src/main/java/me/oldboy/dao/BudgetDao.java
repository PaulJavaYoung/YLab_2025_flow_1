package me.oldboy.dao;

import me.oldboy.data_base.BudgetDB;
import me.oldboy.entity.Budget;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) implementation of the CrudOperation for managing Budget entities.
 */
public class BudgetDao implements CrudOperation<Long, Budget> {

    private static BudgetDao INSTANCE;

    public static BudgetDao getInstance() {
        if(INSTANCE == null){
            INSTANCE = new BudgetDao();
        }
        return INSTANCE;
    }

    private BudgetDB budgetDB = BudgetDB.getInstance();

    @Override
    public List<Budget> findAll() {
        return budgetDB.getBudgetsDb();
    }

    @Override
    public Optional<Budget> findById(Long id) {
        Optional<Budget> mayBeBudget = budgetDB.getBudgetsDb().stream()
                                                              .filter(budget -> budget.getBudgetId().equals(id))
                                                              .findAny();
        return mayBeBudget;
    }

    public Optional<Budget> findBudgetByUserId(Long userId){
        Optional<Budget> currentUserBudget = findAll().stream()
                                            .filter(budget -> budget.getUserId().equals(userId))
                                            .findFirst();

        return currentUserBudget;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Budget> mayBeBudget = budgetDB.getBudgetsDb().stream()
                                                              .filter(budget -> budget.getBudgetId().equals(id))
                                                              .findAny();
        if (mayBeBudget.isPresent()) {
            budgetDB.getBudgetsDb().remove(mayBeBudget.get());
            return true;
        } else {
            return false;

        }
    }

    @Override
    public void update(Budget entity) {
        Optional<Budget> mayBeBudget = findById(entity.getBudgetId());

        if(mayBeBudget.isPresent()){
            int index = findAll().indexOf(mayBeBudget.get());
            findAll().set(index, entity);
        }
    }

    @Override
    public Budget save(Budget entity) {
        Long currentId = budgetDB.getCurrentIdCount();
        Long newBudgetId = currentId + 1L;

        findAll().add(budgetBuilder(newBudgetId, entity));

        return findById(newBudgetId).get();
    }

    private Budget budgetBuilder(Long budgetId, Budget entity){
        return Budget.builder()
                .budgetId(budgetId)
                .userId(entity.getUserId())
                .maximumFlow(entity.getMaximumFlow())
                .build();
    }
}