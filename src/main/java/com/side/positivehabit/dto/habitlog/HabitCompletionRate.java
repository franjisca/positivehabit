package com.side.positivehabit.dto.habitlog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HabitCompletionRate {

    private Long habitId;
    private String habitName;
    private Long totalDays;
    private Long completedDays;

    public Double getCompletionRate() {
        return totalDays > 0 ? (completedDays * 100.0 / totalDays) : 0.0;
    }
}
