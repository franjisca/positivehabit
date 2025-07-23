package com.side.positivehabit.repository.habitphoto;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.positivehabit.domain.habit.HabitPhoto;

import java.time.LocalDate;
import java.util.List;

public class HabitPhotoCustomRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<HabitPhoto> findPublicPhotosByDateRange(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .selectFrom(habitPhoto)
                .where(
                        habitPhoto.isPublic.eq(true),
                        habitPhoto.uploadDate.between(startDate, endDate),
                        habitPhoto.deletedAt.isNull()
                )
                .orderBy(habitPhoto.uploadDate.desc())
                .fetch();
    }

    @Override
    public PhotoStatistics getPhotoStatistics(Long userId, Long habitId) {
        Long totalPhotos = queryFactory
                .select(habitPhoto.count())
                .from(habitPhoto)
                .where(
                        habitPhoto.user.id.eq(userId),
                        habitId != null ? habitPhoto.habit.id.eq(habitId) : null,
                        habitPhoto.deletedAt.isNull()
                )
                .fetchOne();

        Long totalSize = queryFactory
                .select(habitPhoto.fileSize.sum())
                .from(habitPhoto)
                .where(
                        habitPhoto.user.id.eq(userId),
                        habitId != null ? habitPhoto.habit.id.eq(habitId) : null,
                        habitPhoto.deletedAt.isNull()
                )
                .fetchOne();

        return PhotoStatistics.builder()
                .userId(userId)
                .habitId(habitId)
                .totalPhotos(totalPhotos != null ? totalPhotos : 0L)
                .totalSize(totalSize != null ? totalSize : 0L)
                .build();
    }

    @Override
    public List<HabitPhoto> findRecentPhotosByUser(Long userId, int limit) {
        return queryFactory
                .selectFrom(habitPhoto)
                .where(
                        habitPhoto.user.id.eq(userId),
                        habitPhoto.deletedAt.isNull()
                )
                .orderBy(habitPhoto.uploadDate.desc(), habitPhoto.createdAt.desc())
                .limit(limit)
                .fetch();
    }
}
