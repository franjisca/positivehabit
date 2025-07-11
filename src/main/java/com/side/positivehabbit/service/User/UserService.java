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
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        userRepository.save(User.builder()
                .email(dto.email())
                .password(dto.password()) // 추후 암호화 필요
                .nickname(dto.nickname())
                .build());
    }

    public UserResponseDto login(UserRequestDto dto) {
        User user = userRepository.findByEmail(dto.email())
                .filter(u -> u.getPassword().equals(dto.password()))
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패"));
        return UserResponseDto.from(user);
    }
}
