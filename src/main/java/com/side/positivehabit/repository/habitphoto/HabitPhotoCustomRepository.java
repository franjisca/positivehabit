package com.side.positivehabit.repository.habitphoto;

import com.side.positivehabit.domain.habit.HabitPhoto;

import java.time.LocalDate;
import java.util.List;

public interface HabitPhotoCustomRepository {
    List<HabitPhoto> findPublicPhotosByDateRange(LocalDate startDate, LocalDate endDate);
    PhotoStatistics getPhotoStatistics(Long userId, Long habitId);
    List<HabitPhoto> findRecentPhotosByUser(Long userId, int limit);
}
