package com.side.positivehabit.dto.habit;

import com.side.positivehabit.domain.habit.Habit;

public record HabitResponseDto(
        Long id,
        String name,
        String description,
        boolean frequency
) {
    public static HabitResponseDto from(Habit habit) {
        return new HabitResponseDto(
                habit.getId(),
                habit.getName(),
                habit.getDescription(),
                habit.getIsEveryday()
        );
    }
}
