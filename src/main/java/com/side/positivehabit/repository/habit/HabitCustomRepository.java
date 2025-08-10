package com.side.positivehabit.repository.habit;

import com.side.positivehabit.domain.habit.DayOfWeek;
import com.side.positivehabit.domain.habit.Habit;
import com.side.positivehabit.dto.habit.HabitSearchCondition;
import com.side.positivehabit.dto.habit.HabitStatistics;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface HabitCustomRepository {

    Page<Habit> searchHabits(HabitSearchCondition condition, Pageable pageable);
    List<Habit> findHabitsActiveOnDay(Long userId, DayOfWeek dayOfWeek);
    List<Habit> findHabitsWithRemindersAt(int hour, int minute);
    HabitStatistics getHabitStatistics(Long habitId, LocalDate startDate, LocalDate endDate);
    List<Habit> findTopHabitsByCompletionRate(Long userId, int limit);
}
