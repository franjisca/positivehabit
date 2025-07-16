package com.side.positivehabit.service.habit;

import com.side.positivehabit.domain.habit.Habit;
import com.side.positivehabit.domain.user.User;
import com.side.positivehabit.dto.habit.HabitRequestDto;
import com.side.positivehabit.dto.habit.HabitResponseDto;
import com.side.positivehabit.repository.habit.HabitRepository;
import com.side.positivehabit.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public Long createHabit(HabitRequestDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Habit habit = Habit.builder()
                .user(user)
                .name(dto.name())
                .description(dto.description())
                .equals(dto.frequency())
                .build();

        return habitRepository.save(habit).getId();
    }

    public List<HabitResponseDto> getHabitsByUser(Long userId) {
        return habitRepository.findByUserId(userId)
                .stream()
                .map(HabitResponseDto::from)
                .toList();
    }

    public void deleteHabit(Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new IllegalArgumentException("해당 습관을 찾을 수 없습니다."));

        habitRepository.delete(habit);
    }



}