package com.side.positivehabit.repository.emotion;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.positivehabit.domain.emotion.EmotionType;
import com.side.positivehabit.dto.emotion.EmotionStatistics;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EmotionCustomRepositoryImpl implements EmotionCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<EmotionType, Long> getEmotionTypeCount(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Tuple> results = queryFactory
                .select(emotion.emotionType, emotion.count())
                .from(emotion)
                .where(
                        emotion.user.id.eq(userId),
                        emotion.recordDate.between(startDate, endDate)
                )
                .groupBy(emotion.emotionType)
                .fetch();

        return results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(emotion.emotionType),
                        tuple -> tuple.get(1, Long.class)
                ));
    }

    @Override
    public EmotionStatistics getEmotionStatistics(Long userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        Map<EmotionType, Long> emotionCounts = getEmotionTypeCount(userId, startDate, endDate);

        Long totalDays = queryFactory
                .select(emotion.count())
                .from(emotion)
                .where(
                        emotion.user.id.eq(userId),
                        emotion.recordDate.between(startDate, endDate)
                )
                .fetchOne();

        return EmotionStatistics.builder()
                .userId(userId)
                .year(year)
                .month(month)
                .totalRecords(totalDays)
                .emotionCounts(emotionCounts)
                .build();
    }

    @Override
    public Map<LocalDate, EmotionType> getMonthlyEmotions(Long userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<Tuple> results = queryFactory
                .select(emotion.recordDate, emotion.emotionType)
                .from(emotion)
                .where(
                        emotion.user.id.eq(userId),
                        emotion.recordDate.between(startDate, endDate)
                )
                .orderBy(emotion.recordDate.asc())
                .fetch();

        return results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(emotion.recordDate),
                        tuple -> tuple.get(emotion.emotionType)
                ));
    }
}