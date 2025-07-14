package com.side.positivehabit.service.User;

import com.side.positivehabit.domain.User;
import com.side.positivehabit.dto.user.UserRequestDto;
import com.side.positivehabit.dto.user.UserResponseDto;
import com.side.positivehabit.exception.DuplicateEmailException;
import com.side.positivehabit.repository.user.UserRepository;
import com.side.positivehabit.util.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void register(UserRequestDto dto) {
        if (userRepository.existsByEmail((dto.getEmail()))) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .nickname(dto.getNickname())
                .build());
    }

    public UserResponseDto login(UserRequestDto dto) {
        User user = userRepository.findUserByEmailUsingQuerydsl(dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtProvider.generateToken(user.getEmail());
        return UserResponseDto.of(token, user);
    }


}
