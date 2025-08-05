package com.side.positivehabit.config.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job habitReminderJob;

    /**
     * 매일 오전 9시에 습관 리마인더 배치 작업 실행
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void runHabitReminderJob() {
        try {
            log.info("Scheduling habit reminder batch job...");

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(habitReminderJob, jobParameters);

        } catch (Exception e) {
            log.error("Failed to run habit reminder batch job: {}", e.getMessage(), e);
        }
    }

    /**
     * 테스트용 - 매 5분마다 실행 (개발 중에만 사용)
     */
    // @Scheduled(fixedDelay = 300000) // 5분마다
    public void runHabitReminderJobForTest() {
        try {
            log.info("Running habit reminder batch job for testing...");

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(habitReminderJob, jobParameters);

        } catch (Exception e) {
            log.error("Failed to run test habit reminder batch job: {}", e.getMessage(), e);
        }
    }
}
