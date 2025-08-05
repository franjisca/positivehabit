package com.side.positivehabit.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor

public class BatchConfig {

    private DataSource dataSource;

    @Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager());
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.setTablePrefix("BATCH_");
        return factory.getObject();
    }

    @Bean
    @SuppressWarnings("deprecation")
    public JobBuilderFactory jobBuilderFactory() {
        return new JobBuilderFactory(jobRepository);
    }

    @Bean
    @SuppressWarnings("deprecation")
    public StepBuilderFactory stepBuilderFactory() {
        return new StepBuilderFactory(jobRepository, transactionManager);
    }
}