package me.oldboy.data_base;

import me.oldboy.entity.FinGoal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GoalDBTest {
    public GoalDB goalDB;

    @BeforeEach
    public void initBase(){
        goalDB = new GoalDB();
    }

    @AfterEach
    public void killBase(){
        goalDB.getFinGoalsDb().clear();
    }

    @Test
    void shouldReturnCurrentGoalCountTest() {
        FinGoal finGoal = new FinGoal();
        FinGoal finGoal_2 = new FinGoal();
        goalDB.getFinGoalsDb().add(finGoal);
        goalDB.getFinGoalsDb().add(finGoal_2);

        assertThat(goalDB.getCurrentIdCount()).isEqualTo(2);
    }

    @Test
    void shouldReturnCurrentGoalContainInDB() {
        FinGoal finGoal = FinGoal.builder().userId(1L).finGoalVolume(500.0).goalDescription("Покупка...").build();
        FinGoal finGoal_2 = FinGoal.builder().userId(2L).finGoalVolume(800.0).goalDescription("Покупка...!").build();
        goalDB.getFinGoalsDb().add(finGoal);
        goalDB.getFinGoalsDb().add(finGoal_2);

        assertThat(finGoal.getFinGoalVolume()).isEqualTo(goalDB.getFinGoalsDb().get(0).getFinGoalVolume());
        assertThat(finGoal_2.getFinGoalVolume()).isEqualTo(goalDB.getFinGoalsDb().get(1).getFinGoalVolume());
    }

    @Test
    void shouldReturnInitGoalContainInDB() {
        GoalDB goalDB_1 = GoalDB.getInstance();

        assertThat(goalDB_1.getCurrentIdCount()).isEqualTo(3);
    }
}