package com.side.positivehabit.domain.usersettings;

public enum Theme {
    LIGHT("라이트 모드"),
    DARK("다크 모드"),
    AUTO("시스템 설정 따름");

    private final String description;

    Theme(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}