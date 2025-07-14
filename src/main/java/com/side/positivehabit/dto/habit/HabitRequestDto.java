package com.side.positivehabit.dto.habit;

public record HabitRequestDto(
        Long userId,
        String name,
        String description,
        String frequency
) {}