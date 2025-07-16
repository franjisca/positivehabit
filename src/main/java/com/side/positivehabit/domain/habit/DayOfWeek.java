package com.side.positivehabit.domain.habit;

import lombok.Getter;

@Getter
public enum DayOfWeek {
    MONDAY("월요일", "월", 1),
    TUESDAY("화요일", "화", 2),
    WEDNESDAY("수요일", "수", 3),
    THURSDAY("목요일", "목", 4),
    FRIDAY("금요일", "금", 5),
    SATURDAY("토요일", "토", 6),
    SUNDAY("일요일", "일", 7);

    private final String fullName;
    private final String shortName;
    private final int dayNumber;

    DayOfWeek(String fullName, String shortName, int dayNumber) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.dayNumber = dayNumber;
    }

    public static DayOfWeek fromJavaTimeDayOfWeek(java.time.DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> MONDAY;
            case TUESDAY -> TUESDAY;
            case WEDNESDAY -> WEDNESDAY;
            case THURSDAY -> THURSDAY;
            case FRIDAY -> FRIDAY;
            case SATURDAY -> SATURDAY;
            case SUNDAY -> SUNDAY;
        };
    }

    public java.time.DayOfWeek toJavaTimeDayOfWeek() {
        return java.time.DayOfWeek.of(this.dayNumber);
    }
}