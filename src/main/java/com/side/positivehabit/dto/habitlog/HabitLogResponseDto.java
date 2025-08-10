package com.side.positivehabit.dto.habitlog;

import com.side.positivehabit.domain.dailyrecord.DailyRecord;

import java.time.LocalDate;

public record HabitLogResponseDto(
        Long id,
        Long habitId,
        LocalDate date,
        boolean completed,
        String imageUrl // ✅ 포함
) {
    public static HabitLogResponseDto from(DailyRecord log) {
        return new HabitLogResponseDto(
                log.getId(),
                log.getHabit().getId(),
                log.getUpdatedAt().toLocalDate(),
                log.getIsCompleted(),
                log.getImageUrl()
        );
    }
}
