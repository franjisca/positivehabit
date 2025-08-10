package com.side.positivehabit.dto.habitphoto;

import com.side.positivehabit.domain.habit.HabitPhoto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "습관 사진 응답")
public class HabitPhotoResponse {

    @Schema(description = "사진 ID")
    private Long id;

    @Schema(description = "습관 ID")
    private Long habitId;

    @Schema(description = "습관 이름")
    private String habitName;

    @Schema(description = "사진 URL")
    private String photoUrl;

    @Schema(description = "썸네일 URL")
    private String thumbnailUrl;

    @Schema(description = "파일 크기 (bytes)")
    private Long fileSize;

    @Schema(description = "파일 타입")
    private String fileType;

    @Schema(description = "업로드 날짜")
    private LocalDate uploadDate;

    @Schema(description = "설명")
    private String description;

    @Schema(description = "공개 여부")
    private Boolean isPublic;

    @Schema(description = "생성일시")
    private LocalDateTime createdAt;

    public static HabitPhotoResponse from(HabitPhoto photo) {
        return HabitPhotoResponse.builder()
                .id(photo.getId())
                .habitId(photo.getHabit().getId())
                .habitName(photo.getHabit().getName())
                .photoUrl(photo.getPhotoUrl())
                .thumbnailUrl(photo.getThumbnailUrl())
                .fileSize(photo.getFileSize())
                .fileType(photo.getFileType())
                .uploadDate(photo.getUploadDate())
                .description(photo.getDescription())
                .isPublic(photo.getIsPublic())
                .createdAt(photo.getCreatedAt())
                .build();
    }
}

