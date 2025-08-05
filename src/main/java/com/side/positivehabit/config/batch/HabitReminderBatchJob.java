package com.side.positivehabit.config.batch;

import com.side.positivehabit.config.batch.listener.HabitReminderStepListener;
import com.side.positivehabit.config.batch.listener.JobCompletionListener;
import com.side.positivehabit.config.batch.processor.HabitReminderProcessor;
import com.side.positivehabit.config.batch.reader.HabitReminderReader;
import com.side.positivehabit.config.batch.writer.NotificationWriter;
import com.side.positivehabit.domain.habit.Habit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HabitReminderBatchJob {

    // 🔄 Factory들 제거하고 이렇게 변경
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final HabitReminderReader habitReminderReader;
    private final HabitReminderProcessor habitReminderProcessor;
    private final NotificationWriter notificationWriter;
    private final JobCompletionListener jobCompletionListener;

    @Bean
    public Job habitReminderJob() {
        // 🔄 jobBuilderFactory.get() → new JobBuilder()
        return new JobBuilder("habitReminderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionListener)
                .flow(habitReminderStep())
                .end()
                .build();
    }

    @Bean
    public Step habitReminderStep() {
        // 🔄 stepBuilderFactory.get() → new StepBuilder()
        return new StepBuilder("habitReminderStep", jobRepository)
                .<Habit, NotificationMessage>chunk(100, transactionManager)
                .reader(habitReminderReader)
                .processor(habitReminderProcessor)
                .writer(notificationWriter)
                .build();
    }
}