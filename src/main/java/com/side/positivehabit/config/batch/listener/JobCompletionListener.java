package com.side.positivehabit.config.batch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        LocalDateTime startTime = jobExecution.getStartTime();

        log.info("=== 배치 작업 시작 ===");
        log.info("Job Name: {}", jobName);
        log.info("Job ID: {}", jobExecution.getId());
        log.info("Start Time: {}", startTime);
        log.info("========================");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        BatchStatus status = jobExecution.getStatus();
        LocalDateTime startTime = jobExecution.getStartTime();
        LocalDateTime endTime = jobExecution.getEndTime();

        log.info("=== 배치 작업 완료 ===");
        log.info("Job Name: {}", jobName);
        log.info("Job ID: {}", jobExecution.getId());
        log.info("Status: {}", status);
        log.info("Start Time: {}", startTime);
        log.info("End Time: {}", endTime);

        if (startTime != null && endTime != null) {
            Duration duration = Duration.between(startTime, endTime);
            log.info("Duration: {} seconds", duration.getSeconds());
        }

        // 통계 정보 로깅
        jobExecution.getStepExecutions().forEach(stepExecution -> {
            log.info("Step: {} | Read: {} | Write: {} | Skip: {}",
                    stepExecution.getStepName(),
                    stepExecution.getReadCount(),
                    stepExecution.getWriteCount(),
                    stepExecution.getSkipCount());
        });

        if (status == BatchStatus.COMPLETED) {
            log.info("🎉 배치 작업이 성공적으로 완료되었습니다!");
        } else if (status == BatchStatus.FAILED) {
            log.error("❌ 배치 작업이 실패했습니다.");

            // 실패 원인 로깅
            jobExecution.getAllFailureExceptions().forEach(throwable -> {
                log.error("Failure reason: {}", throwable.getMessage(), throwable);
            });
        }
        log.info("========================");
    }
}