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
public class EmotionStatistics {

    private Long userId;
    private Integer year;
    private Integer month;
    private Long totalRecords;
    private Map<EmotionType, Long> emotionCounts;
}