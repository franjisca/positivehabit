package com.side.positivehabit.dto.habitlog;

import com.side.positivehabit.domain.dailyrecord.DailyRecord;
import com.side.positivehabit.domain.habit.Habit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "일일 기록 응답")
public class DailyRecordResponse {

    @Schema(description = "기록 ID")
    private Long id;

    @Schema(description = "습관 ID")
    private Long habitId;

    @Schema(description = "습관 이름")
    private String habitName;

    @Schema(description = "습관 이모지")
    private String habitEmoji;

    @Schema(description = "습관 색상")
    private String habitColor;

    @Schema(description = "기록 날짜")
    private LocalDate recordDate;

    @Schema(description = "완료 여부")
    private Boolean isCompleted;

    @Schema(description = "메모")
    private String notes;

    @Schema(description = "인증샷 필요 여부")
    private Boolean needsPhoto;

    public static DailyRecordResponse from(DailyRecord record) {
        return DailyRecordResponse.builder()
                .id(record.getId())
                .habitId(record.getHabit().getId())
                .habitName(record.getHabit().getName())
                .habitEmoji(record.getHabit().getEmoji())
                .habitColor(record.getHabit().getColor())
                .recordDate(record.getRecordDate())
                .isCompleted(record.getIsCompleted())
                .notes(record.getNotes())
                .needsPhoto(record.getHabit().getNeedsPhoto())
                .build();
    }

    public static DailyRecordResponse empty(Habit habit, LocalDate date) {
        return DailyRecordResponse.builder()
                .id(null)
                .habitId(habit.getId())
                .habitName(habit.getName())
                .habitEmoji(habit.getEmoji())
                .habitColor(habit.getColor())
                .recordDate(date)
                .isCompleted(false)
                .notes(null)
                .needsPhoto(habit.getNeedsPhoto())
                .build();
    }
}
