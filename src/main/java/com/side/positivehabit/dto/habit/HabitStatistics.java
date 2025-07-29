package com.side.positivehabit.dto.habit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitStatistics {

    private Long habitId;
    private Long totalDays;
    private Long completedDays;
    private Double completionRate;
    private LocalDate startDate;
    private LocalDate endDate;
}
