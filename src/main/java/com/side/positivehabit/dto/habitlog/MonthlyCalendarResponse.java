package com.side.positivehabit.dto.habitlog;

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
@Schema(description = "월별 캘린더 응답")
public class MonthlyCalendarResponse {

    @Schema(description = "년도")
    private Integer year;

    @Schema(description = "월")
    private Integer month;

    @Schema(description = "일별 데이터")
    private List<DayData> days;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DayData {

        @Schema(description = "날짜")
        private LocalDate date;

        @Schema(description = "완료한 습관 수")
        private Integer completedCount;

        @Schema(description = "전체 습관 수")
        private Integer totalCount;

        @Schema(description = "완료율 (%)")
        private Double completionRate;
    }
}
