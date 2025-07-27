package com.side.positivehabit.config.batch;

import com.side.positivehabit.domain.habit.Habit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HabitReminderBatchJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final HabitReminderReader habitReminderReader;
    private final HabitReminderProcessor habitReminderProcessor;
    private final NotificationWriter notificationWriter;
    private final JobCompletionListener jobCompletionListener;

    @Bean
    public Job habitReminderJob() {
        return jobBuilderFactory.get("habitReminderJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionListener)
                .flow(habitReminderStep())
                .end()
                .build();
    }

    @Bean
    public Step habitReminderStep() {
        return stepBuilderFactory.get("habitReminderStep")
                .<Habit, NotificationMessage>chunk(100)
                .reader(habitReminderReader)
                .processor(habitReminderProcessor)
                .writer(notificationWriter)
                .build();
    }
}
