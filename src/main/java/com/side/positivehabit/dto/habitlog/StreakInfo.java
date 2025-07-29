package com.side.positivehabit.dto.habitlog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreakInfo {

    private Integer currentStreak;
    private Integer maxStreak;
    private LocalDate lastCompletedDate;
}