package com.side.positivehabit.dto.habitphoto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사진 설명 수정 요청")
public class PhotoDescriptionUpdateRequest {

    @Schema(description = "사진 설명")
    @Size(max = 500, message = "설명은 500자 이하로 작성해주세요.")
    private String description;
}
