package com.side.positivehabit.repository.habitlog;

import com.side.positivehabit.domain.habitlog.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
    List<HabitLog> findByHabitId(Long habitId);
    HabitLog findByHabitIdAndDate(Long habitId, LocalDate date);
}