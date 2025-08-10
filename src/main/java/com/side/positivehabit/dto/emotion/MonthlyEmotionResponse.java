package com.side.positivehabit.dto.emotion;

import com.side.positivehabit.domain.emotion.EmotionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "월별 감정 응답")
public class MonthlyEmotionResponse {

    @Schema(description = "년도")
    private Integer year;

    @Schema(description = "월")
    private Integer month;

    @Schema(description = "일별 감정 목록")
    private List<DayEmotion> emotions;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DayEmotion {

        @Schema(description = "날짜")
        private LocalDate date;

        @Schema(description = "감정 타입")
        private EmotionType emotionType;

        @Schema(description = "감정 이모지")
        private String emoji;
    }
}