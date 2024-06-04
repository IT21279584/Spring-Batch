package com.example.JobParameterValidator;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    @Bean
    @JobScope
    public CustomItemReader customItemReader(@Value("#{jobExecution.id}") Long jobExecutionId){
        List<String> items = Arrays.asList("item1", "item2", "item3", "valueToFilter1");
        return new CustomItemReader(items,"item");
    }

    @Bean
    public CustomItemProcessor customItemProcessor(){
        return new CustomItemProcessor();
    }

    @Bean
    public CustomItemWriter customItemWriter(){
        return new CustomItemWriter();
    }

    @Bean
    public CustomJobExecutionListener customJobExecutionListener(){
        return new CustomJobExecutionListener();
    }
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step step, CustomJobExecutionListener listener ){
        return jobBuilderFactory.get("job")
                .validator(new CustomJobParameterValidator())
                .listener(listener)
                .flow(step)
                .end().build();
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory, CustomItemReader reader, CustomItemWriter writer, CustomItemProcessor processor){
        return stepBuilderFactory.get("step")
                .<String, String>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
