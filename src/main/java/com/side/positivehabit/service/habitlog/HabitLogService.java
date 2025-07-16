package com.side.positivehabit.service.habitlog;

import com.side.positivehabit.domain.habit.Habit;
import com.side.positivehabit.domain.HabitLog;
import com.side.positivehabit.dto.habitlog.HabitLogRequestDto;
import com.side.positivehabit.dto.habitlog.HabitLogResponseDto;
import com.side.positivehabit.repository.habit.HabitRepository;
import com.side.positivehabit.repository.habitlog.HabitLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitLogService {

    private final HabitLogRepository habitLogRepository;
    private final HabitRepository habitRepository;

    public Long saveLog(HabitLogRequestDto dto) {
        Habit habit = habitRepository.findById(dto.habitId())
                .orElseThrow(() -> new IllegalArgumentException("습관이 존재하지 않습니다."));

        HabitLog log = HabitLog.builder()
                .habit(habit)
                .date(dto.date())
                .completed(dto.completed())
                .imageUrl(dto.imageUrl())
                .build();

        return habitLogRepository.save(log).getId();
    }

    public List<HabitLogResponseDto> getLogsByHabit(Long habitId) {
        return habitLogRepository.findByHabitId(habitId)
                .stream()
                .map(HabitLogResponseDto::from)
                .toList();
    }

    public HabitLogResponseDto getLogByHabitAndDate(Long habitId, String date) {
        HabitLog log = habitLogRepository.findByHabitIdAndDate(habitId, LocalDate.parse(date));

        if (log == null) {
            throw new IllegalArgumentException("해당 날짜의 로그가 없습니다.");
        }

        return HabitLogResponseDto.from(log);
    }
}