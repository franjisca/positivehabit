package com.side.positivehabit.dto.emotion;

import com.side.positivehabit.domain.emotion.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "감정 통계 응답")
public class EmotionStatisticsResponse {

    @Schema(description = "년도")
    private Integer year;

    @Schema(description = "월")
    private Integer month;

    @Schema(description = "총 기록 수")
    private Long totalRecords;

    @Schema(description = "감정별 카운트")
    private Map<EmotionType, Long> emotionCounts;

    @Schema(description = "감정별 비율 (%)")
    private Map<EmotionType, Double> emotionPercentages;

    @Schema(description = "가장 많은 감정")
    private EmotionType mostFrequentEmotion;
}

