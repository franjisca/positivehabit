package com.side.positivehabit.config.batch.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HabitReminderStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        String stepName = stepExecution.getStepName();
        log.info("=== Step 시작: {} ===", stepName);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String stepName = stepExecution.getStepName();
        ExitStatus exitStatus = stepExecution.getExitStatus();

        log.info("=== Step 완료: {} ===", stepName);
        log.info("Exit Status: {}", exitStatus.getExitCode());
        log.info("Read Count: {}", stepExecution.getReadCount());
        log.info("Write Count: {}", stepExecution.getWriteCount());
        log.info("Skip Count: {}", stepExecution.getSkipCount());

        if (stepExecution.getFailureExceptions().size() > 0) {
            log.error("Step failures:");
            stepExecution.getFailureExceptions().forEach(throwable -> {
                log.error("- {}", throwable.getMessage());
            });
        }

        return exitStatus;
    }
}