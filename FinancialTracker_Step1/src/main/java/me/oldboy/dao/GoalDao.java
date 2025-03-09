package me.oldboy.dao;

import me.oldboy.data_base.GoalDB;
import me.oldboy.entity.FinGoal;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) implementation of the CrudOperation for managing FinGoal entities.
 */
public class GoalDao implements CrudOperation<Long, FinGoal>{

    private static GoalDao INSTANCE;

    public static GoalDao getInstance() {
        if(INSTANCE == null){
            INSTANCE = new GoalDao();
        }
        return INSTANCE;
    }

    private GoalDB goalDB = GoalDB.getInstance();

    @Override
    public List<FinGoal> findAll() {
        return goalDB.getFinGoalsDb();
    }

    @Override
    public Optional<FinGoal> findById(Long id) {
        Optional<FinGoal> mayBeGoal = findAll().stream()
                                               .filter(goal -> goal.getGoalId().equals(id))
                                               .findAny();
        return mayBeGoal;
    }

    public List<FinGoal> findGoalsByUserId(Long userId){
        List<FinGoal> currentUserGoalsList = findAll().stream()
                                                      .filter(goal -> goal.getUserId().equals(userId))
                                                      .toList();

        return currentUserGoalsList;
    }

    @Override
    public boolean delete(Long id) {
        Optional<FinGoal> mayBeGoal = findAll().stream()
                                               .filter(goal -> goal.getGoalId().equals(id))
                                               .findAny();
        if(mayBeGoal.isPresent()){
            findAll().remove(mayBeGoal.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update(FinGoal entity) {

        Optional<FinGoal> mayBeGoal = findById(entity.getGoalId());

        if(mayBeGoal.isPresent()){
            int index = findAll().indexOf(mayBeGoal.get());
            findAll().set(index, entity);
        }
    }

    @Override
    public FinGoal save(FinGoal entity) {

        Long currentId = goalDB.getCurrentIdCount();
        Long newGoalId = currentId + 1L;

        findAll().add(goalBuilder(newGoalId, entity));

        return findById(newGoalId).get();
    }

    private FinGoal goalBuilder(Long goalId, FinGoal entity){
        return FinGoal.builder()
                      .goalId(goalId)
                      .userId(entity.getUserId())
                      .finGoalVolume(entity.getFinGoalVolume())
                      .goalDescription(entity.getGoalDescription())
                      .build();
    }
}
