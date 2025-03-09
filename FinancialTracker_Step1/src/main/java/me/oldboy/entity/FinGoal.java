package me.oldboy.entity;

import lombok.*;

import java.util.Objects;

/**
 * Represents a financial goal with information such as a unique id, userId, goal volume, and goal description.
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FinGoal {
    private Long goalId;
    private Long userId;
    private Double finGoalVolume;
    private String goalDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinGoal finGoal = (FinGoal) o;
        return Objects.equals(goalId, finGoal.goalId) && Objects.equals(userId, finGoal.userId) && Objects.equals(finGoalVolume, finGoal.finGoalVolume) && Objects.equals(goalDescription, finGoal.goalDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goalId, userId, finGoalVolume, goalDescription);
    }

    @Override
    public String toString() {
        return "FinGoal {" +
                "goalId = " + goalId +
                ", userId = " + userId +
                ", goalVolume = " + finGoalVolume +
                ", description = '" + goalDescription + '\'' +
                '}';
    }
}
