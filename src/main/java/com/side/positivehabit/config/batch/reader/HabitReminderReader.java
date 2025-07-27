package com.side.positivehabit.config.batch.reader;

import com.side.positivehabit.domain.habit.Habit;
import com.side.positivehabit.repository.habit.HabitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HabitReminderReader implements ItemReader<Habit> {

    private final HabitRepository habitRepository;
    private Iterator<Habit> habitIterator;

    @Override
    public Habit read() {
        if (habitIterator == null) {
            initializeIterator();
        }

        if (habitIterator != null && habitIterator.hasNext()) {
            return habitIterator.next();
        }

        return null; // 더 이상 읽을 데이터가 없음
    }

    private void initializeIterator() {
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        int currentMinute = currentTime.getMinute();

        // 현재 시간에 알림이 설정된 습관들 조회
        List<Habit> habits = habitRepository.findHabitsWithRemindersAt(currentHour, currentMinute);

        log.info("Found {} habits with reminders at {}:{}",
                habits.size(), currentHour, currentMinute);

        if (!habits.isEmpty()) {
            habitIterator = habits.iterator();
        }
    }
}