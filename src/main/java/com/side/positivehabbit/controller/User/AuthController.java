package com.side.positivehabbit.controller.User;

import com.side.positivehabbit.domain.User;
import com.side.positivehabbit.dto.user.UserRequestDto;
import com.side.positivehabbit.dto.user.UserResponseDto;
import com.side.positivehabbit.repository.user.UserRepository;
import com.side.positivehabbit.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto dto) {
        userService.register(dto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserRequestDto dto) {

        UserResponseDto responseDto = userService.login(dto);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + responseDto.accessToken())
                .body(userService.login(dto));
    }
}