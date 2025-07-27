package com.side.positivehabit.config.batch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "job_execution_id")
    private Long jobExecutionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NotificationStatus status;

    @Column(name = "message", length = 2000)
    private String message;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "retry_count", nullable = false)
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "max_retry", nullable = false)
    @Builder.Default
    private Integer maxRetry = 3;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = NotificationStatus.PENDING;
        }
    }

    /**
     * 재시도 가능 여부 확인
     */
    public boolean canRetry() {
        return retryCount < maxRetry &&
                (status == NotificationStatus.PENDING || status == NotificationStatus.RETRY);
    }

    /**
     * 재시도 횟수 증가
     */
    public void incrementRetryCount() {
        this.retryCount++;
        this.status = NotificationStatus.RETRY;
    }

    /**
     * 발송 완료로 상태 변경
     */
    public void markAsSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.errorMessage = null;
    }

    /**
     * 발송 실패로 상태 변경
     */
    public void markAsFailed(String errorMessage) {
        this.status = NotificationStatus.FAILED;
        this.errorMessage = errorMessage;
    }

    /**
     * 재시도 상태로 변경
     */
    public void markAsRetry() {
        this.status = NotificationStatus.RETRY;
    }

    /**
     * 발송 대기 상태로 변경
     */
    public void markAsPending() {
        this.status = NotificationStatus.PENDING;
        this.errorMessage = null;
    }
}
