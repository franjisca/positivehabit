package com.side.positivehabit.repository.dailyrecord;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.positivehabit.domain.dailyrecord.DailyRecord;
import com.side.positivehabit.dto.habitlog.DailyRecordStatistics;
import com.side.positivehabit.dto.habitlog.HabitCompletionRate;
import com.side.positivehabit.dto.habitlog.StreakInfo;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class DailyRecordCustomRepositoryImpl implements DailyRecordCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Long, Boolean> findCompletionStatusByUserAndDate(Long userId, LocalDate date) {
        List<Tuple> results = queryFactory
                .select(dailyRecord.habit.id, dailyRecord.isCompleted)
                .from(dailyRecord)
                .where(
                        dailyRecord.user.id.eq(userId),
                        dailyRecord.recordDate.eq(date)
                )
                .fetch();

        return results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(dailyRecord.habit.id),
                        tuple -> tuple.get(dailyRecord.isCompleted)
                ));
    }

    @Override
    public List<DailyRecordStatistics> getDailyStatistics(Long userId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(Projections.constructor(DailyRecordStatistics.class,
                        dailyRecord.recordDate,
                        dailyRecord.count(),
                        dailyRecord.isCompleted.when(true).then(1L).otherwise(0L).sum()
                ))
                .from(dailyRecord)
                .where(
                        dailyRecord.user.id.eq(userId),
                        dailyRecord.recordDate.between(startDate, endDate)
                )
                .groupBy(dailyRecord.recordDate)
                .orderBy(dailyRecord.recordDate.asc())
                .fetch();
    }

    @Override
    public List<HabitCompletionRate> getHabitCompletionRates(Long userId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(Projections.constructor(HabitCompletionRate.class,
                        habit.id,
                        habit.name,
                        dailyRecord.count(),
                        dailyRecord.isCompleted.when(true).then(1L).otherwise(0L).sum()
                ))
                .from(dailyRecord)
                .join(dailyRecord.habit, habit)
                .where(
                        dailyRecord.user.id.eq(userId),
                        dailyRecord.recordDate.between(startDate, endDate),
                        habit.isActive.eq(true)
                )
                .groupBy(habit.id, habit.name)
                .fetch();
    }

    @Override
    public StreakInfo calculateStreak(Long userId, Long habitId) {
        List<DailyRecord> records = queryFactory
                .selectFrom(dailyRecord)
                .where(
                        dailyRecord.user.id.eq(userId),
                        dailyRecord.habit.id.eq(habitId),
                        dailyRecord.isCompleted.eq(true)
                )
                .orderBy(dailyRecord.recordDate.desc())
                .fetch();

        if (records.isEmpty()) {
            return StreakInfo.builder()
                    .currentStreak(0)
                    .maxStreak(0)
                    .lastCompletedDate(null)
                    .build();
        }

        // 현재 스트릭 계산
        int currentStreak = 0;
        LocalDate today = LocalDate.now();
        LocalDate checkDate = today;

        for (DailyRecord record : records) {
            if (record.getRecordDate().equals(checkDate) ||
                    record.getRecordDate().equals(checkDate.minusDays(1))) {
                currentStreak++;
                checkDate = record.getRecordDate().minusDays(1);
            } else {
                break;
            }
        }

        // 최대 스트릭 계산
        int maxStreak = currentStreak;
        int tempStreak = 1;

        for (int i = 1; i < records.size(); i++) {
            if (records.get(i).getRecordDate().plusDays(1).equals(records.get(i-1).getRecordDate())) {
                tempStreak++;
                maxStreak = Math.max(maxStreak, tempStreak);
            } else {
                tempStreak = 1;
            }
        }

        return StreakInfo.builder()
                .currentStreak(currentStreak)
                .maxStreak(maxStreak)
                .lastCompletedDate(records.get(0).getRecordDate())
                .build();
    }

    @Override
    public Map<LocalDate, Long> getMonthlyCompletionCount(Long userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<Tuple> results = queryFactory
                .select(dailyRecord.recordDate, dailyRecord.count())
                .from(dailyRecord)
                .where(
                        dailyRecord.user.id.eq(userId),
                        dailyRecord.recordDate.between(startDate, endDate),
                        dailyRecord.isCompleted.eq(true)
                )
                .groupBy(dailyRecord.recordDate)
                .fetch();

        return results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(dailyRecord.recordDate),
                        tuple -> tuple.get(1, Long.class)
                ));
    }
}
