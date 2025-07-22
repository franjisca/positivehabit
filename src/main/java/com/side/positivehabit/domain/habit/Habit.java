package com.side.positivehabit.domain.habit;


import com.side.positivehabit.domain.common.BaseTimeEntity;
import com.side.positivehabit.domain.habitlog.HabitLog;
import com.side.positivehabit.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "habits",
        indexes = {
                @Index(name = "idx_habit_user_id", columnList = "user_id"),
                @Index(name = "idx_habit_is_active", columnList = "isActive"),
                @Index(name = "idx_habit_deleted_at", columnList = "deletedAt")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE habits SET deleted_at = NOW() WHERE id = ?")
public class Habit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "emoji", length = 10)
    private String emoji;

    @Column(name = "color", length = 7) // HEX color code
    private String color;

    @ElementCollection
    @CollectionTable(
            name = "habit_selected_days",
            joinColumns = @JoinColumn(name = "habit_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    @Builder.Default
    private List<DayOfWeek> selectedDays = new ArrayList<>(); // 선택된 요일들

    @Column(name = "is_everyday", nullable = false)
    @Builder.Default
    private Boolean isEveryday = true; // true면 매일, false면 선택한 요일만

    @Column(name = "reminder_time")
    private LocalTime reminderTime;

    @Column(name = "needs_photo", nullable = false)
    @Builder.Default
    private Boolean needsPhoto = false;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "streak_count")
    @Builder.Default
    private Integer streakCount = 0;

    @Column(name = "max_streak")
    @Builder.Default
    private Integer maxStreak = 0;

    @Column(name = "total_completed_count")
    @Builder.Default
    private Integer totalCompletedCount = 0;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HabitLog> dailyRecords = new ArrayList<>();

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HabitPhoto> habitPhotos = new ArrayList<>();

    // 비즈니스 메서드
    public void updateHabit(String name, String description, String emoji, String color,
                            Boolean isEveryday, List<DayOfWeek> selectedDays,
                            LocalTime reminderTime, Boolean needsPhoto) {
        this.name = name;
        this.description = description;
        this.emoji = emoji;
        this.color = color;
        this.isEveryday = isEveryday;
        this.selectedDays.clear();
        if (selectedDays != null && !isEveryday) {
            this.selectedDays.addAll(selectedDays);
        }
        this.reminderTime = reminderTime;
        this.needsPhoto = needsPhoto;
    }

    public void setEveryday() {
        this.isEveryday = true;
        this.selectedDays.clear();
    }

    public void setSelectedDays(List<DayOfWeek> days) {
        this.isEveryday = false;
        this.selectedDays.clear();
        if (days != null && !days.isEmpty()) {
            this.selectedDays.addAll(days);
        }
    }

    public boolean isActiveOnDay(DayOfWeek day) {
        if (isEveryday) {
            return true;
        }
        return selectedDays.contains(day);
    }

    public void toggleActive() {
        this.isActive = !this.isActive;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void incrementStreak() {
        this.streakCount++;
        if (this.streakCount > this.maxStreak) {
            this.maxStreak = this.streakCount;
        }
    }

    public void resetStreak() {
        this.streakCount = 0;
    }

    public void incrementCompletedCount() {
        this.totalCompletedCount++;
    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }

    public void addDailyRecord(HabitLog dailyRecord) {
        dailyRecords.add(dailyRecord);
        dailyRecord.setHabit(this);
    }

    public void removeDailyRecord(HabitLog dailyRecord) {
        dailyRecords.remove(dailyRecord);
        dailyRecord.setHabit(null);
    }

    public void addHabitPhoto(HabitPhoto habitPhoto) {
        habitPhotos.add(habitPhoto);
        habitPhoto.setHabit((Habit) this.habitPhotos);
    }

    public void removeHabitPhoto(HabitPhoto habitPhoto) {
        habitPhotos.remove(habitPhoto);
        habitPhoto.setHabit(null);
    }
}
