package com.side.positivehabit.domain.usersettings;

import com.side.positivehabit.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_settings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserSettings extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 알림 설정
    @Column(name = "push_notifications_enabled", nullable = false)
    @Builder.Default
    private Boolean pushNotificationsEnabled = true;

    @Column(name = "email_notifications_enabled", nullable = false)
    @Builder.Default
    private Boolean emailNotificationsEnabled = false;

    @Column(name = "reminder_notifications_enabled", nullable = false)
    @Builder.Default
    private Boolean reminderNotificationsEnabled = true;

    // 앱 설정
    @Enumerated(EnumType.STRING)
    @Column(name = "theme", nullable = false, length = 20)
    @Builder.Default
    private Theme theme = Theme.LIGHT;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false, length = 20)
    @Builder.Default
    private Language language = Language.KOREAN;

    // 프라이버시 설정
    @Column(name = "profile_public", nullable = false)
    @Builder.Default
    private Boolean profilePublic = false;

    @Column(name = "share_statistics", nullable = false)
    @Builder.Default
    private Boolean shareStatistics = false;

    // 기타 설정
    @Column(name = "sound_effects_enabled", nullable = false)
    @Builder.Default
    private Boolean soundEffectsEnabled = true;

    @Column(name = "haptic_feedback_enabled", nullable = false)
    @Builder.Default
    private Boolean hapticFeedbackEnabled = true;

    @Column(name = "auto_dark_mode", nullable = false)
    @Builder.Default
    private Boolean autoDarkMode = false;

    // 위젯 설정
    @Column(name = "widget_enabled", nullable = false)
    @Builder.Default
    private Boolean widgetEnabled = false;

    @Column(name = "widget_transparency")
    @Builder.Default
    private Integer widgetTransparency = 100; // 0-100%

    // 연관관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // 비즈니스 메서드
    public void updateNotificationSettings(Boolean pushEnabled, Boolean emailEnabled, Boolean reminderEnabled) {
        if (pushEnabled != null) {
            this.pushNotificationsEnabled = pushEnabled;
        }
        if (emailEnabled != null) {
            this.emailNotificationsEnabled = emailEnabled;
        }
        if (reminderEnabled != null) {
            this.reminderNotificationsEnabled = reminderEnabled;
        }
    }

    public void updateAppSettings(Theme theme, Language language) {
        if (theme != null) {
            this.theme = theme;
        }
        if (language != null) {
            this.language = language;
        }
    }

    public void toggleTheme() {
        this.theme = (this.theme == Theme.LIGHT) ? Theme.DARK : Theme.LIGHT;
    }

    public void updatePrivacySettings(Boolean profilePublic, Boolean shareStatistics) {
        if (profilePublic != null) {
            this.profilePublic = profilePublic;
        }
        if (shareStatistics != null) {
            this.shareStatistics = shareStatistics;
        }
    }

    public void updateUxSettings(Boolean soundEffects, Boolean hapticFeedback, Boolean autoDarkMode) {
        if (soundEffects != null) {
            this.soundEffectsEnabled = soundEffects;
        }
        if (hapticFeedback != null) {
            this.hapticFeedbackEnabled = hapticFeedback;
        }
        if (autoDarkMode != null) {
            this.autoDarkMode = autoDarkMode;
        }
    }

    public void updateWidgetSettings(Boolean enabled, Integer transparency) {
        if (enabled != null) {
            this.widgetEnabled = enabled;
        }
        if (transparency != null && transparency >= 0 && transparency <= 100) {
            this.widgetTransparency = transparency;
        }
    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }

    // 정적 팩토리 메서드
    public static UserSettings createDefaultSettings(User user) {
        UserSettings settings = UserSettings.builder().build();
        settings.setUser(user);
        return settings;
    }
}