package com.side.positivehabit.domain.emotion;

import lombok.Getter;

@Getter
public enum EmotionType {
    HAPPY("행복", "😊"),
    NORMAL("보통", "😐"),
    SAD("슬픔", "😢"),
    ANGRY("화남", "😠"),
    TIRED("피곤", "😴");

    private final String description;
    private final String defaultEmoji;

    EmotionType(String description, String defaultEmoji) {
        this.description = description;
        this.defaultEmoji = defaultEmoji;
    }

    public static EmotionType fromEmoji(String emoji) {
        for (EmotionType type : values()) {
            if (type.defaultEmoji.equals(emoji)) {
                return type;
            }
        }
        // 기본값으로 NORMAL 반환
        return NORMAL;
    }
}