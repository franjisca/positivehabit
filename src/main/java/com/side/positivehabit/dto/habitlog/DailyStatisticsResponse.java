package com.side.positivehabit.dto.habitlog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "일일 통계 응답")
public class DailyStatisticsResponse {

    @Schema(description = "날짜")
    private LocalDate date;

    @Schema(description = "전체 습관 수")
    private Long totalHabits;

    @Schema(description = "완료한 습관 수")
    private Long completedHabits;

    @Schema(description = "완료율 (%)")
    private Double completionRate;
}
