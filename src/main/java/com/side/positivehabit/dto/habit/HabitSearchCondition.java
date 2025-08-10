package com.side.positivehabit.dto.habit;

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
@Schema(description = "습관 검색 조건")
public class HabitSearchCondition {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "습관 이름")
    private String name;

    @Schema(description = "활성 상태")
    private Boolean isActive;

    @Schema(description = "인증샷 필요 여부")
    private Boolean needsPhoto;

    @Schema(description = "생성일 시작")
    private LocalDate createdFrom;

    @Schema(description = "생성일 종료")
    private LocalDate createdTo;
}