package com.example.MultiStepsTasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.persistence.EntityManagerFactory;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public JpaPagingItemReader<Student> reader() {
        return new JpaPagingItemReaderBuilder<Student>()
                .name("StudentReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT s FROM student s")
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemProcessor<Student, Student> processor() {
        return new StudentProcessor1();
    }

    @Bean
    public ItemWriter<Student> writer(StudentRepository repository) {
        return items -> {
            for (Student item : items) {
                repository.save(item);
            }
        };
    }

    @Bean
    public JpaPagingItemReader<Student> reader2() {
        return new JpaPagingItemReaderBuilder<Student>()
                .name("StudentReader2")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT s FROM student s")
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemProcessor<Student, NewStudent> processor2() {
        return new StudentProcessor2();
    }

    @Bean
    public ItemWriter<NewStudent> writer2(NewStudentRepository newStudentRepo) {
        return items -> {
            for (NewStudent item : items) {
                newStudentRepo.save(item);
            }
        };
    }

    @Bean
    public Step step1(ItemReader<Student> reader, ItemProcessor<Student, Student> processor, ItemWriter<Student> writer) {
        return stepBuilderFactory.get("step1")
                .<Student, Student>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step step2(ItemReader<Student> reader2, ItemProcessor<Student, NewStudent> processor2, ItemWriter<NewStudent> writer2) {
        return stepBuilderFactory.get("step2")
                .<Student, NewStudent>chunk(10)
                .reader(reader2)
                .processor(processor2)
                .writer(writer2)
                .build();
    }

    @Bean
    public Job importUserJob(Step step1, Step step2) {
        return jobBuilderFactory.get("importUserJob")
                .start(step1)
                .next(step2)
                .build();
    }
}