package com.side.positivehabit.domain.usersettings;

public enum Language {
    KOREAN("한국어"),
    ENGLISH("English"),
    JAPANESE("日本語"),
    CHINESE("中文");

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}