package com.example.springbatchexercise2.config;


import com.example.springbatchexercise2.component.StudentItemProcessor;
import com.example.springbatchexercise2.component.StudentItemWriter;
import com.example.springbatchexercise2.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@EnableBatchProcessing
@Configuration
public class SpringBatchConfig {
    public static Logger logger = LoggerFactory.getLogger(SpringBatchConfig.class);
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JpaPagingItemReader<Student> studentItemReader(EntityManagerFactory entityManagerFactory) {
        JpaPagingItemReader<Student> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT s FROM Student s");
        reader.setPageSize(10);

        reader.setSaveState(false);
        reader.open(null); // Open reader
        try {
            Student student = null;
            while ((student = reader.read()) != null) {
                logger.info("Read student: {}", student.getFirstname());
            }
        } catch (Exception e) {
            logger.error("Error reading students", e);
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                logger.error("Error closing reader", e);
            }
        }

        return reader;
    }


    @Bean
    public Job processStudentJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                 ItemReader<Student>studentItemReader, ItemProcessor<Student, Student> studentItemProcessor,
                                 ItemWriter<Student> studentItemWriter) {
        Step step = stepBuilderFactory.get("processStudentJob")
                .<Student, Student>chunk(10)
                .reader(studentItemReader)
                .processor(studentItemProcessor)
                .writer(studentItemWriter)
                .allowStartIfComplete(true)
                .build();

        return jobBuilderFactory.get("processStudentJob")
                .start(step)
                .build();
    }
    @Bean
    public StudentItemProcessor processor(){
        return new StudentItemProcessor();
    }

    @Bean
    public StudentItemWriter studentItemWriter(){
        return new StudentItemWriter();
    }

}
