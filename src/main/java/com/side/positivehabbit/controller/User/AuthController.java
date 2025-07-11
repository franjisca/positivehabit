package com.side.positivehabbit.controller.User;

import com.side.positivehabbit.dto.user.UserRequestDto;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto dto) {
        userService.register(dto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<UserRequestDto.UserResponseDto> login(@RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }
}