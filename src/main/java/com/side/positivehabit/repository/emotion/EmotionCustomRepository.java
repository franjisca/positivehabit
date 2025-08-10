package com.side.positivehabit.repository.emotion;

import com.side.positivehabit.domain.emotion.EmotionType;
import com.side.positivehabit.dto.emotion.EmotionStatistics;

import java.time.LocalDate;
import java.util.Map;

public interface EmotionCustomRepository {
    Map<EmotionType, Long> getEmotionTypeCount(Long userId, LocalDate startDate, LocalDate endDate);
    EmotionStatistics getEmotionStatistics(Long userId, int year, int month);
    Map<LocalDate, EmotionType> getMonthlyEmotions(Long userId, int year, int month);
}