package com.side.positivehabit.config.batch.processor;

import com.side.positivehabit.config.batch.NotificationMessage;
import com.side.positivehabit.config.batch.NotificationStatus;
import com.side.positivehabit.domain.habit.Habit;
import com.side.positivehabit.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.repository.persistence.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 알림 메시지 모델 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HabitReminderProcessor implements ItemProcessor<Habit, NotificationMessage> {

    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public NotificationMessage process(Habit habit) throws Exception {
        // 사용자 설정 확인
        if (!shouldSendNotification(habit)) {
            log.debug("Skipping notification for habit: {} - notification not allowed", habit.getId());
            return null;
        }

        try {
            log.debug("Processing habit reminder for habit: {}", habit.getId());

            User user = habit.getUser();
            String userEmail = user.getEmail();

            // 습관 알림 메시지 생성
            String reminderMessage = createReminderMessage(habit, user);

            // NotificationMessage 객체 생성
            NotificationMessage notificationMessage = NotificationMessage.builder()
                    .jobName("habitReminderJob")
                    .jobExecutionId(stepExecution != null ? stepExecution.getJobExecutionId() : null)
                    .status(NotificationStatus.PENDING)
                    .message(reminderMessage)
                    .recipient(userEmail)
                    .retryCount(0)
                    .maxRetry(3)
                    .build();

            log.info("Created notification for habit: {} (User: {}, Email: {})",
                    habit.getName(), user.getName(), userEmail);

            return notificationMessage;

        } catch (Exception e) {
            log.error("Error processing habit reminder for habit {}: {}",
                    habit.getId(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 알림 발송 여부를 판단하는 메서드
     */
    private boolean shouldSendNotification(Habit habit) {
        // 습관이 null인 경우
        if (habit == null) {
            log.warn("Habit is null, skipping notification");
            return false;
        }

        // 사용자 정보 확인
        User user = habit.getUser();
        if (user == null) {
            log.warn("Habit {} has no associated user, skipping", habit.getId());
            return false;
        }

        // 사용자 이메일 확인
        String userEmail = user.getEmail();
        if (userEmail == null || userEmail.trim().isEmpty()) {
            log.warn("User {} has no email address, skipping habit {}",
                    user.getId(), habit.getId());
            return false;
        }

        // 습관이 활성화되어 있는지 확인
        if (!habit.isActive()) {
            log.debug("Habit {} is not active, skipping notification", habit.getId());
            return false;
        }

        // 사용자가 알림을 허용했는지 확인 (User 엔티티에 알림 설정 필드가 있다면)
        if (user.getNotificationEnabled() != null && !user.getNotificationEnabled()) {
            log.debug("User {} has disabled notifications, skipping", user.getId());
            return false;
        }

        // 습관별 알림 설정 확인 (Habit 엔티티에 알림 설정 필드가 있다면)
        if (habit.getReminderEnabled() != null && !habit.getReminderEnabled()) {
            log.debug("Habit {} has reminder disabled, skipping", habit.getId());
            return false;
        }

        // 오늘 이미 완료된 습관인지 확인 (선택적)
        // if (isHabitCompletedToday(habit)) {
        //     log.debug("Habit {} is already completed today, skipping reminder", habit.getId());
        //     return false;
        // }

        return true;
    }

    /**
     * 습관 알림 메시지를 생성하는 메서드
     */
    private String createReminderMessage(Habit habit, User user) {
        StringBuilder message = new StringBuilder();

        // 개인화된 인사
        message.append("안녕하세요, ").append(user.getName()).append("님! 🌟\n\n");

        // 습관 정보
        message.append("📝 습관 실천 시간입니다!\n");
        message.append("오늘의 습관: ").append(habit.getName()).append("\n");

        // 습관 설명 (있는 경우)
        if (habit.getDescription() != null && !habit.getDescription().trim().isEmpty()) {
            message.append("설명: ").append(habit.getDescription()).append("\n");
        }

        // 목표 횟수 정보 (있는 경우)
        if (habit.getTargetCount() != null && habit.getTargetCount() > 0) {
            message.append("목표 횟수: ").append(habit.getTargetCount()).append("회\n");
        }

        // 현재 시간
        message.append("\n⏰ 알림 시간: ")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .append("\n");

        // 격려 메시지
        message.append("\n💪 작은 실천이 큰 변화를 만듭니다!");
        message.append("\n지금 바로 시작해보세요! 🚀");

        return message.toString();
    }

    /**
     * 오늘 습관이 완료되었는지 확인하는 메서드 (선택적 구현)
     */
    private boolean isHabitCompletedToday(Habit habit) {
        // HabitRecord나 HabitCompletion 엔티티가 있다면 여기서 확인
        // 예시:
        // LocalDate today = LocalDate.now();
        // return habitRecordRepository.existsByHabitIdAndCompletionDate(habit.getId(), today);

        return false; // 기본값: 완료되지 않음으로 처리
    }

    /**
     * 습관 카테고리별 맞춤 메시지 생성 (선택적)
     */
    private String getCategoryMessage(String category) {
        if (category == null) {
            return "오늘도 좋은 습관을 실천해보세요! ✨";
        }

        switch (category.toLowerCase()) {
            case "운동":
            case "exercise":
                return "건강한 몸을 위한 운동 시간이에요! 💪";
            case "독서":
            case "reading":
                return "지식을 쌓는 독서 시간입니다! 📚";
            case "명상":
            case "meditation":
                return "마음의 평화를 찾는 명상 시간이에요! 🧘‍♀️";
            case "물마시기":
            case "water":
                return "건강을 위해 물을 마실 시간입니다! 💧";
            case "공부":
            case "study":
                return "성장을 위한 학습 시간이에요! 📖";
            default:
                return "오늘도 좋은 습관을 실천해보세요! ✨";
        }
    }
}

private String createReminderMessage(Habit habit, User user) {
    StringBuilder message = new StringBuilder();

    // 사용자 이름으로 개인화된 인사
    message.append("안녕하세요, ").append(user.getName()).append("님! 🌟\n\n");

    // 습관 리마인더 메시지
    message.append("📝 습관 실천 시간입니다!\n");
    message.append("오늘의 습관: ").append(habit.getName()).append("\n");

    // 습관 설명이 있다면 추가
    if (habit.getDescription() != null && !habit.getDescription().trim().isEmpty()) {
        message.append("설명: ").append(habit.getDescription()).append("\n");
    }

    // 목표 횟수 정보
    if (habit.getTargetCount() != null && habit.getTargetCount() > 0) {
        message.append("목표 횟수: ").append(habit.getTargetCount()).append("회\n");
    }

    // 현재 시간 정보
    message.append("\n⏰ 알림 시간: ")
            .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .append("\n");

    // 격려 메시지
    message.append("\n💪 작은 실천이 큰 변화를 만듭니다!");
    message.append("\n지금 바로 시작해보세요! 🚀");

    // 앱 링크나 추가 안내 (필요시)
    message.append("\n\n📱 앱에서 실천 기록하기");

    return message.toString();
}

/**
 * 습관 카테고리별 맞춤 메시지 생성 (선택적 구현)
 */
private String createCategorySpecificMessage(Habit habit) {
    String category = habit.getCategory();

    if (category == null) {
        return "오늘도 좋은 습관을 실천해보세요! ✨";
    }

    switch (category.toLowerCase()) {
        case "운동":
        case "exercise":
            return "건강한 몸을 위한 운동 시간이에요! 💪";
        case "독서":
        case "reading":
            return "지식을 쌓는 독서 시간입니다! 📚";
        case "명상":
        case "meditation":
            return "마음의 평화를 찾는 명상 시간이에요! 🧘‍♀️";
        case "물마시기":
        case "water":
            return "건강을 위해 물을 마실 시간입니다! 💧";
        case "공부":
        case "study":
            return "성장을 위한 학습 시간이에요! 📖";
        default:
            return "오늘도 좋은 습관을 실천해보세요! ✨";
    }
}

/**
 * 시간대별 맞춤 인사말 생성 (선택적 구현)
 */
private String getTimeBasedGreeting() {
    int hour = LocalDateTime.now().getHour();

    if (hour >= 5 && hour < 12) {
        return "좋은 아침입니다! 🌅";
    } else if (hour >= 12 && hour < 17) {
        return "좋은 오후입니다! ☀️";
    } else if (hour >= 17 && hour < 21) {
        return "좋은 저녁입니다! 🌆";
    } else {
        return "늦은 시간이지만 화이팅! 🌙";
    }
}

/**
 * 고급 메시지 생성 (카테고리와 시간대 고려)
 */
private String createAdvancedReminderMessage(Habit habit, User user) {
    StringBuilder message = new StringBuilder();

    // 시간대별 인사
    message.append(getTimeBasedGreeting()).append("\n");
    message.append(user.getName()).append("님! 🌟\n\n");

    // 습관 정보
    message.append("📝 ").append(habit.getName()).append(" 시간이에요!\n");

    // 카테고리별 메시지
    message.append(createCategorySpecificMessage(habit)).append("\n\n");

    // 습관 설명
    if (habit.getDescription() != null && !habit.getDescription().trim().isEmpty()) {
        message.append("📋 ").append(habit.getDescription()).append("\n\n");
    }

    // 목표 정보
    if (habit.getTargetCount() != null && habit.getTargetCount() > 0) {
        message.append("🎯 오늘의 목표: ").append(habit.getTargetCount()).append("회\n\n");
    }

    // 격려 메시지
    message.append("💪 꾸준함이 만드는 기적을 믿어요!");
    message.append("\n지금 바로 시작해보세요! 🚀");

    return message.toString();
}
}