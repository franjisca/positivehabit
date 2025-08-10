package com.side.positivehabit.dto.habitphoto;

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
@Schema(description = "사진 업로드 응답")
public class PhotoUploadResponse {

    @Schema(description = "사진 ID")
    private Long photoId;

    @Schema(description = "사진 URL")
    private String photoUrl;

    @Schema(description = "썸네일 URL")
    private String thumbnailUrl;

    @Schema(description = "업로드 날짜")
    private LocalDate uploadDate;

    @Schema(description = "메시지")
    private String message;
}
