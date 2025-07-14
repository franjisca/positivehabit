package com.side.positivehabit.dto.habitlog;


import java.time.LocalDate;

public record HabitLogRequestDto(
        Long habitId,
        LocalDate date,
        boolean completed,
        String imageUrl

) {
}