package com.side.positivehabbit.dto.user;

import com.side.positivehabbit.domain.User;

public record UserResponseDto(
        Long id,
        String email,
        String nickname,
        String accessToken
) {
    public static UserResponseDto of(String token, User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                token
        );
    }

}
