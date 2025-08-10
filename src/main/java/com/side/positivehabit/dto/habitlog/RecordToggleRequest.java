package com.side.positivehabit.dto.habitlog;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "습관 완료 토글 요청")
public class RecordToggleRequest {

    @Schema(description = "습관 ID", required = true)
    @NotNull(message = "습관 ID는 필수입니다.")
    private Long habitId;

    @Schema(description = "기록 날짜 (null이면 오늘)")
    private LocalDate date;
}