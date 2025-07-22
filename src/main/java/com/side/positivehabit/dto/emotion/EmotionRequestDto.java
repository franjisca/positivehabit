package com.side.positivehabit.dto.emotion;

public record EmotionRequestDto (
    Long userId,
    String emotion,
    String diary
) {}