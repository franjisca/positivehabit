package com.side.positivehabit.config.batch;

import lombok.Getter;

@Getter
public enum NotificationStatus {
    PENDING("대기중", "알림 발송 대기 상태"),
    SENT("발송완료", "알림이 성공적으로 발송됨"),
    FAILED("발송실패", "알림 발송이 실패하고 재시도 불가"),
    RETRY("재시도", "알림 발송 실패 후 재시도 대기 상태");

    private final String displayName;
    private final String description;

    NotificationStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * 완료된 상태인지 확인 (성공 또는 최종 실패)
     */
    public boolean isCompleted() {
        return this == SENT || this == FAILED;
    }

    /**
     * 처리 중인 상태인지 확인 (대기 또는 재시도)
     */
    public boolean isPending() {
        return this == PENDING || this == RETRY;
    }

    /**
     * 성공 상태인지 확인
     */
    public boolean isSuccess() {
        return this == SENT;
    }

    /**
     * 실패 상태인지 확인 (재시도 포함)
     */
    public boolean isFailed() {
        return this == FAILED || this == RETRY;
    }
}
