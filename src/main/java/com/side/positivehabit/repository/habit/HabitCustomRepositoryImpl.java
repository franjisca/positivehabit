package com.side.positivehabit.repository.habit;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.positivehabit.domain.habit.DayOfWeek;
import com.side.positivehabit.domain.habit.Habit;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.side.positivehabit.domain.habit.QHabit.habit;
import static com.side.positivehabit.domain.habitlog.QHabitLog.habitLog;
import static com.side.positivehabit.domain.user.QUser.user;

public class HabitCustomRepositoryImpl implements HabitCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Habit> searchHabits(HabitSearchCondition condition, Pageable pageable) {
        List<Habit> content = queryFactory
                .selectFrom(habit)
                .leftJoin(habit.user, user).fetchJoin()
                .where(
                        userIdEq(condition.getUserId()),
                        nameContains(condition.getName()),
                        isActiveEq(condition.getIsActive()),
                        needsPhotoEq(condition.getNeedsPhoto()),
                        createdDateBetween(condition.getCreatedFrom(), condition.getCreatedTo())
                )
                .orderBy(habit.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(habit.count())
                .from(habit)
                .where(
                        userIdEq(condition.getUserId()),
                        nameContains(condition.getName()),
                        isActiveEq(condition.getIsActive()),
                        needsPhotoEq(condition.getNeedsPhoto()),
                        createdDateBetween(condition.getCreatedFrom(), condition.getCreatedTo())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Habit> findHabitsActiveOnDay(Long userId, java.time.DayOfWeek dayOfWeek) {
        DayOfWeek customDayOfWeek = DayOfWeek.fromJavaTimeDayOfWeek(dayOfWeek);

        return queryFactory
                .selectFrom(habit)
                .where(
                        habit.user.id.eq(userId),
                        habit.isActive.eq(true),
                        habit.deletedAt.isNull(),
                        habit.isEveryday.eq(true)
                                .or(habit.selectedDays.contains(customDayOfWeek))
                )
                .fetch();
    }

    @Override
    public List<Habit> findHabitsWithRemindersAt(int hour, int minute) {
        LocalTime reminderTime = LocalTime.of(hour, minute);

        return queryFactory
                .selectFrom(habit)
                .where(
                        habit.reminderTime.eq(reminderTime),
                        habit.isActive.eq(true),
                        habit.deletedAt.isNull()
                )
                .fetch();
    }

    @Override
    public HabitStatistics getHabitStatistics(Long habitId, LocalDate startDate, LocalDate endDate) {
        Long totalDays = queryFactory
                .select(habitLog.count())
                .from(habitLog)
                .where(
                        habitLog.habit.id.eq(habitId),
                        habitLog.date.between(startDate, endDate)
                )
                .fetchOne();

        Long completedDays = queryFactory
                .select(habitLog.count())
                .from(habitLog)
                .where(
                        habitLog.habit.id.eq(habitId),
                        habitLog.date.between(startDate, endDate),
                        habitLog.completed.eq(true)
                )
                .fetchOne();

        Double completionRate = totalDays > 0 ? (completedDays.doubleValue() / totalDays.doubleValue()) * 100 : 0.0;

        return HabitStatistics.builder()
                .habitId(habitId)
                .totalDays(totalDays)
                .completedDays(completedDays)
                .completionRate(completionRate)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    @Override
    public List<Habit> findTopHabitsByCompletionRate(Long userId, int limit) {
        return queryFactory
                .selectFrom(habit)
                .where(
                        habit.user.id.eq(userId),
                        habit.isActive.eq(true),
                        habit.deletedAt.isNull()
                )
                .orderBy(habit.totalCompletedCount.desc())
                .limit(limit)
                .fetch();
    }

    // 동적 쿼리 조건 메서드들
    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? habit.user.id.eq(userId) : null;
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? habit.name.contains(name) : null;
    }

    private BooleanExpression isActiveEq(Boolean isActive) {
        return isActive != null ? habit.isActive.eq(isActive) : null;
    }

    private BooleanExpression needsPhotoEq(Boolean needsPhoto) {
        return needsPhoto != null ? habit.needsPhoto.eq(needsPhoto) : null;
    }

    private BooleanExpression createdDateBetween(LocalDate from, LocalDate to) {
        if (from != null && to != null) {
            return habit.createdAt.between(from.atStartOfDay(), to.plusDays(1).atStartOfDay());
        } else if (from != null) {
            return habit.createdAt.goe(from.atStartOfDay());
        } else if (to != null) {
            return habit.createdAt.loe(to.plusDays(1).atStartOfDay());
        }
        return null;
    }
}
