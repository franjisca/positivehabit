package com.side.positivehabit.domain.dailyrecord;

import com.side.positivehabit.domain.common.BaseTimeEntity;
import com.side.positivehabit.domain.habit.Habit;
import com.side.positivehabit.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "daily_records",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "habit_id", "record_date"})
        },
        indexes = {
                @Index(name = "idx_daily_record_user_date", columnList = "user_id, record_date"),
                @Index(name = "idx_daily_record_habit_date", columnList = "habit_id, record_date"),
                @Index(name = "idx_daily_record_date", columnList = "record_date")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DailyRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(name = "is_completed", nullable = false)
    @Builder.Default
    private Boolean isCompleted = false;

    @Column(name = "notes", length = 500)
    private String notes;

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    // 비즈니스 메서드
    public void complete() {
        this.isCompleted = true;
        this.habit.incrementCompletedCount();
        updateHabitStreak();
    }

    public void uncomplete() {
        this.isCompleted = false;
        // 완료 카운트는 감소시키지 않음 (데이터 정합성 문제 방지)
        updateHabitStreak();
    }

    public void toggle() {
        if (this.isCompleted) {
            uncomplete();
        } else {
            complete();
        }
    }

    public void updateNotes(String notes) {
        this.notes = notes;
    }

    private void updateHabitStreak() {
        // 스트릭 업데이트 로직은 서비스 레이어에서 처리
        // 여기서는 플래그만 변경
    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
        habit.addDailyRecord(this);
    }

    // 정적 팩토리 메서드
    public static DailyRecord createDailyRecord(User user, Habit habit, LocalDate date) {
        DailyRecord record = DailyRecord.builder()
                .recordDate(date)
                .isCompleted(false)
                .build();
        record.setUser(user);
        record.setHabit(habit);
        return record;
    }
}