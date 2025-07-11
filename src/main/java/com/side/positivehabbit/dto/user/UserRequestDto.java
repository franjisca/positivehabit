package com.side.positivehabbit.dto.user;

import com.side.positivehabbit.domain.User;

public class UserRequestDto {

    public UserRequestDto() {
    }


    public record UserResponseDto(Long id, String email, String nickname) {
        public static UserResponseDto from(User user) {
            return new UserResponseDto(user.getId(), user.getEmail(), user.getNickname());
        }


    }

    }
