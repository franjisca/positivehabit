package com.side.positivehabit.domain.emotion;

import com.side.positivehabit.domain.common.BaseTimeEntity;
import com.side.positivehabit.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmotionLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion_type", nullable = false, length = 20)
    private EmotionType emotionType;

    @Column(name = "emoji", nullable = false, length = 10)
    private String emoji;

    @Column(name = "diary", length = 1000)
    private String diary;

    @Column(name = "is_private", nullable = false)
    @Builder.Default
    private Boolean isPrivate = true;

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 비즈니스 메서드
    public void updateEmotionLog(EmotionType emotionType, String emoji, String diary) {
        this.emotionType = emotionType;
        this.emoji = emoji;
        this.diary = diary;
    }

    public void updateDiary(String diary) {
        this.diary = diary;
    }

    public void togglePrivacy() {
        this.isPrivate = !this.isPrivate;
    }

    public void makePrivate() {
        this.isPrivate = true;
    }

    public void makePublic() {
        this.isPrivate = false;
    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }

    // 정적 팩토리 메서드
    public static EmotionLog createEmotion(User user, LocalDate date, EmotionType emotionType,
                                        String emoji, String diary) {
        EmotionLog emotion = EmotionLog.builder()
                .recordDate(date)
                .emotionType(emotionType)
                .emoji(emoji)
                .diary(diary)
                .build();
        emotion.setUser(user);
        return emotion;
    }
}

