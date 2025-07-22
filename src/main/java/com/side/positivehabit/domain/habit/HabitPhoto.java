package com.side.positivehabit.domain.habit;

import com.side.positivehabit.domain.common.BaseTimeEntity;
import com.side.positivehabit.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "habit_photos",
        indexes = {
                @Index(name = "idx_habit_photo_user_date", columnList = "user_id, upload_date"),
                @Index(name = "idx_habit_photo_habit_date", columnList = "habit_id, upload_date"),
                @Index(name = "idx_habit_photo_deleted_at", columnList = "deletedAt")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE habit_photos SET deleted_at = NOW() WHERE id = ?")
public class HabitPhoto extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "photo_url", nullable = false, length = 500)
    private String photoUrl;

    @Column(name = "photo_key", nullable = false, length = 255)
    private String photoKey; // S3 key for deletion

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(name = "file_size")
    private Long fileSize; // bytes

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "upload_date", nullable = false)
    private LocalDate uploadDate;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_public", nullable = false)
    @Builder.Default
    private Boolean isPublic = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    // 비즈니스 메서드
    public void updateDescription(String description) {
        this.description = description;
    }

    public void togglePrivacy() {
        this.isPublic = !this.isPublic;
    }

    public void makePublic() {
        this.isPublic = true;
    }

    public void makePrivate() {
        this.isPublic = false;
    }

    public void updateThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
        habit.addHabitPhoto(this);
    }

    // 정적 팩토리 메서드
    public static HabitPhoto createHabitPhoto(User user, Habit habit, String photoUrl,
                                              String photoKey, LocalDate uploadDate,
                                              Long fileSize, String fileType) {
        HabitPhoto photo = HabitPhoto.builder()
                .photoUrl(photoUrl)
                .photoKey(photoKey)
                .uploadDate(uploadDate)
                .fileSize(fileSize)
                .fileType(fileType)
                .build();
        photo.setUser(user);
        photo.setHabit(habit);
        return photo;
    }
}