package com.side.positivehabit.dto.user;

import com.side.positivehabit.domain.user.Provider;
import com.side.positivehabit.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 검색 조건")
public class UserSearchCondition {

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "가입 경로")
    private Provider provider;

    @Schema(description = "권한")
    private Role role;

    @Schema(description = "활성 상태")
    private Boolean isActive;

    @Schema(description = "가입일 시작")
    private LocalDateTime createdFrom;

    @Schema(description = "가입일 종료")
    private LocalDateTime createdTo;
}