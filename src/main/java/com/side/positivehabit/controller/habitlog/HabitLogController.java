package com.side.positivehabit.controller.habitlog;

import com.side.positivehabit.dto.habitlog.HabitLogRequestDto;
import com.side.positivehabit.dto.habitlog.HabitLogResponseDto;
import com.side.positivehabit.service.habitlog.HabitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habit-logs")
@RequiredArgsConstructor
public class HabitLogController {

    private final HabitLogService habitLogService;

    @PostMapping
    public ResponseEntity<Long> saveLog(@RequestBody HabitLogRequestDto dto) {
        return ResponseEntity.ok(habitLogService.saveLog(dto));
    }

    @GetMapping("/habit/{habitId}")
    public ResponseEntity<List<HabitLogResponseDto>> getLogsByHabit(@PathVariable Long habitId) {
        return ResponseEntity.ok(habitLogService.getLogsByHabit(habitId));
    }

    @GetMapping("/habit/{habitId}/date")
    public ResponseEntity<HabitLogResponseDto> getLogByDate(
            @PathVariable Long habitId,
            @RequestParam String date
    ) {
        return ResponseEntity.ok(habitLogService.getLogByHabitAndDate(habitId, date));
    }
}