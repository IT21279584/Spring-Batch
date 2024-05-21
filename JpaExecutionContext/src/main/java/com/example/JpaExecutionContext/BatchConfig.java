package com.example.JpaExecutionContext;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;



    @Autowired
    private ItemProcess exampleProcessor;

    @Autowired
    private ItemReader<String> reader;

    @Autowired
    private ItemWriter<String> writer;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .repository(jobRepository)
                .start(step())
                .listener(jobExecutionListener()) // Use the new name here
                .build();
    }


    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .repository(jobRepository)
                .<String, String>chunk(10)
                .reader(reader)
                .processor(exampleProcessor)
                .writer(writer)
                .allowStartIfComplete(true)
                .transactionManager(transactionManager)
                .build();
    }
    @Bean
    public JobListener jobExecutionListener() {
        return new JobListener();
    }

    @Bean
    public ItemReader<String> reader() {
        List<String> items = Arrays.asList("item1", "item2", "item3");
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> items.forEach(System.out::println);
    }

}
