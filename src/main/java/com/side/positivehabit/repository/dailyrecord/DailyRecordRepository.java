package com.side.positivehabit.repository.dailyrecord;

import com.side.positivehabit.domain.habit.Habit;
import com.side.positivehabit.domain.DailyRecord.DailyRecord;
import com.side.positivehabit.domain.DailyRecord.DailyRecord;
import com.side.positivehabit.domain.user.User;
import com.side.positivehabit.repository.habit.HabitCustomRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long>, DailyRecordCustomRepository {
    List<DailyRecord> findByHabitId(Long habitId);
    DailyRecord findByHabitIdAndDate(Long habitId, LocalDate date);
    Optional<DailyRecord> findByUserAndHabitAndRecordDate(User user, Habit habit, LocalDate recordDate);

    List<DailyRecord> findByUserAndRecordDate(User user, LocalDate recordDate);

    List<DailyRecord> findByHabitAndRecordDateBetween(Habit habit, LocalDate startDate, LocalDate endDate);

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.user.id = :userId AND dr.recordDate = :date")
    List<DailyRecord> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.habit.id = :habitId AND dr.recordDate BETWEEN :startDate AND :endDate ORDER BY dr.recordDate DESC")
    List<DailyRecord> findHabitRecordsBetweenDates(@Param("habitId") Long habitId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(dr) FROM DailyRecord dr WHERE dr.habit.id = :habitId AND dr.isCompleted = true")
    long countCompletedByHabitId(@Param("habitId") Long habitId);

    boolean existsByUserAndHabitAndRecordDate(User user, Habit habit, LocalDate recordDate);
}