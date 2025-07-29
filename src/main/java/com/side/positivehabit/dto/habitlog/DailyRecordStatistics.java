package com.side.positivehabit.dto.habitlog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRecordStatistics {

    private LocalDate date;
    private Long totalCount;
    private Long completedCount;

    public Double getCompletionRate() {
        return totalCount > 0 ? (completedCount * 100.0 / totalCount) : 0.0;
    }
}