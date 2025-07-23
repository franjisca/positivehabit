package com.side.positivehabit.repository.habit;


import com.side.positivehabit.domain.habit.Habit;
import com.side.positivehabit.domain.user.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long>, HabitCustomRepository {
    List<Habit> findByUserId(Long userId);
    List<Habit> findByUserAndIsActiveTrue(User user);

    List<Habit> findByUserOrderByCreatedAtDesc(User user);

    @Query("SELECT h FROM Habit h WHERE h.user.id = :userId AND h.isActive = true AND h.deletedAt IS NULL")
    List<Habit> findActiveHabitsByUserId(@Param("userId") Long userId);

    @Query("SELECT h FROM Habit h LEFT JOIN FETCH h.dailyRecords WHERE h.id = :id")
    Optional<Habit> findByIdWithRecords(@Param("id") Long id);

    @Query("SELECT COUNT(h) FROM Habit h WHERE h.user.id = :userId AND h.isActive = true")
    long countActiveHabitsByUserId(@Param("userId") Long userId);

    boolean existsByIdAndUserId(Long id, Long userId);
}