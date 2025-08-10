package com.side.positivehabit.repository.emotion;

import com.side.positivehabit.domain.emotion.EmotionLog;
import com.side.positivehabit.domain.user.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmotionRepository extends JpaRepository<EmotionLog, Long>, EmotionCustomRepository {

    Optional<EmotionLog> findByUserAndRecordDate(User user, LocalDate recordDate);

    List<EmotionLog> findByUserAndRecordDateBetween(User user, LocalDate startDate, LocalDate endDate);

    @Query("SELECT e FROM EmotionLog e WHERE e.user.id = :userId AND e.recordDate = :date")
    Optional<EmotionLog> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT e FROM EmotionLog e WHERE e.user.id = :userId ORDER BY e.recordDate DESC")
    List<EmotionLog> findRecentEmotions(@Param("userId") Long userId);

    boolean existsByUserAndRecordDate(User user, LocalDate recordDate);
}
