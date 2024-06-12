package com.example.Junit.config;


import com.example.Junit.model.Employee;
import com.example.Junit.processor.EmployeeItemProcessor;
import com.example.Junit.repository.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Bean
    public RepositoryItemReader<Employee> employeeReader() {
        return new RepositoryItemReaderBuilder<Employee>()
                .name("employeeReader")
                .repository(employeeRepository)
                .methodName("findAll")
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public EmployeeItemProcessor processor() {
        return new EmployeeItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<Employee> writer() {
        return new RepositoryItemWriterBuilder<Employee>()
                .repository(employeeRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public Step employeeStep() {
        return stepBuilderFactory.get("employeeStep")
                .<Employee, Employee>chunk(10)
                .reader(employeeReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job processEmployeeJob() {
        return jobBuilderFactory.get("processEmployeeJob")
                .incrementer(new RunIdIncrementer())
                .flow(employeeStep())
                .end()
                .build();
    }
}


