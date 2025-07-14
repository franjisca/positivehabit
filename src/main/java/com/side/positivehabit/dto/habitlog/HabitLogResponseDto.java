package com.side.positivehabit.dto.habitlog;

import com.side.positivehabit.domain.HabitLog;

import java.time.LocalDate;

public record HabitLogResponseDto(
        Long id,
        Long habitId,
        LocalDate date,
        boolean completed,
        String imageUrl // ✅ 포함
) {
    public static HabitLogResponseDto from(HabitLog log) {
        return new HabitLogResponseDto(
                log.getId(),
                log.getHabit().getId(),
                log.getDate(),
                log.isCompleted(),
                log.getImageUrl()
        );
    }
}
