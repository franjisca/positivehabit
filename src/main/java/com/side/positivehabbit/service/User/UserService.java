package com.side.positivehabbit.service.User;

import com.side.positivehabbit.domain.User;
import com.side.positivehabbit.dto.user.UserRequestDto;
import com.side.positivehabbit.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void register(UserRequestDto dto) {
        if (userRepository.existsByEmail((dto.getEmail()))) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword()) // 추후 암호화 필요
                .nickname(dto.getNickname())
                .build());
    }

    public UserRequestDto login(UserRequestDto dto) {

        User user = userRepository
                .findByEmail(dto.getEmail())
                ;
        return dto;
    }


}
