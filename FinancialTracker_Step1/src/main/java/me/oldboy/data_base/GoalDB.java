package me.oldboy.data_base;

import lombok.Getter;
import lombok.Setter;
import me.oldboy.entity.FinGoal;

import java.util.ArrayList;
import java.util.List;

/**
 * Financial goals database imitation.
 */
public class GoalDB {

    private static GoalDB INSTANCE;

    public static GoalDB getInstance() {
        if(INSTANCE == null){
            INSTANCE = new GoalDB();
            initialSetup();
        }
        return INSTANCE;
    }

    @Setter
    private Long goalIdCount;

    @Getter
    private List<FinGoal> finGoalsDb = new ArrayList<>();

    public Long getCurrentIdCount() {
        this.goalIdCount = Long.valueOf(finGoalsDb.size());
        return goalIdCount;
    }

    private static void initialSetup(){
        FinGoal finGoal_1 = FinGoal.builder()
                .goalId(1L)
                .userId(2L)
                .finGoalVolume(450.0)
                .goalDescription("Бобровая ферма")
                .build();

        FinGoal finGoal_2 = FinGoal.builder()
                .goalId(2L)
                .userId(2L)
                .finGoalVolume(550.0)
                .goalDescription("Охотничья заимка")
                .build();

        FinGoal finGoal_3 = FinGoal.builder()
                .goalId(3L)
                .userId(1L)
                .finGoalVolume(1_000_000.0)
                .goalDescription("Покупка обломков 'Настромо'")
                .build();

        INSTANCE.finGoalsDb.add(finGoal_1);
        INSTANCE.finGoalsDb.add(finGoal_2);
        INSTANCE.finGoalsDb.add(finGoal_3);
    }
}
