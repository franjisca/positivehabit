package com.side.positivehabit.repository.dailyrecord;

import java.time.LocalDate;
import java.util.Map;

public interface DailyRecordCustomRepository {
    Map<Long, Boolean> findCompletionStatusByUserAndDate(Long userId, LocalDate date);
    List<DailyRecordStatistics> getDailyStatistics(Long userId, LocalDate startDate, LocalDate endDate);
    List<HabitCompletionRate> getHabitCompletionRates(Long userId, LocalDate startDate, LocalDate endDate);
    StreakInfo calculateStreak(Long userId, Long habitId);
    Map<LocalDate, Long> getMonthlyCompletionCount(Long userId, int year, int month);
}
