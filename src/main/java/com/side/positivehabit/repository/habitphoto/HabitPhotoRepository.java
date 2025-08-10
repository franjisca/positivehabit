package com.side.positivehabit.repository.habitphoto;

import com.side.positivehabit.domain.habit.Habit;
import com.side.positivehabit.domain.habit.HabitPhoto;
import com.side.positivehabit.domain.user.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitPhotoRepository extends JpaRepository<HabitPhoto, Long>, HabitPhotoCustomRepository {

    List<HabitPhoto> findByHabitAndUploadDate(Habit habit, LocalDate uploadDate);

    Optional<HabitPhoto> findByIdAndUser(Long id, User user);

    @Query("SELECT hp FROM HabitPhoto hp WHERE hp.user.id = :userId AND hp.uploadDate = :date")
    List<HabitPhoto> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT hp FROM HabitPhoto hp WHERE hp.habit.id = :habitId ORDER BY hp.uploadDate DESC")
    Page<HabitPhoto> findByHabitId(@Param("habitId") Long habitId, Pageable pageable);

    @Query("SELECT COUNT(hp) FROM HabitPhoto hp WHERE hp.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    void deleteByPhotoKey(String photoKey);
}
