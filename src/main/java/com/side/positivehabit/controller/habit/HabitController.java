package com.side.positivehabit.controller.habit;

import com.side.positivehabit.dto.habit.HabitRequestDto;
import com.side.positivehabit.dto.habit.HabitResponseDto;
import com.side.positivehabit.service.habit.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping
    public ResponseEntity<Long> createHabit(@RequestBody HabitRequestDto dto) {
        Long habitId = habitService.createHabit(dto);
        return ResponseEntity.ok(habitId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HabitResponseDto>> getHabitsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(habitService.getHabitsByUser(userId));
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<?> deleteHabit(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);
        return ResponseEntity.ok("습관이 삭제되었습니다.");
    }
}
