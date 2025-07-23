package com.side.positivehabit.repository.emotion;

import com.side.positivehabit.domain.user.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Long>, EmotionCustomRepository {

    Optional<Emotion> findByUserAndRecordDate(User user, LocalDate recordDate);

    List<Emotion> findByUserAndRecordDateBetween(User user, LocalDate startDate, LocalDate endDate);

    @Query("SELECT e FROM Emotion e WHERE e.user.id = :userId AND e.recordDate = :date")
    Optional<Emotion> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT e FROM Emotion e WHERE e.user.id = :userId ORDER BY e.recordDate DESC")
    List<Emotion> findRecentEmotions(@Param("userId") Long userId);

    boolean existsByUserAndRecordDate(User user, LocalDate recordDate);
}
