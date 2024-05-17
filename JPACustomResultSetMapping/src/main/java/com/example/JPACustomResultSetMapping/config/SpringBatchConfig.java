package com.example.JPACustomResultSetMapping.config;

import com.example.JPACustomResultSetMapping.component.*;
import com.example.JPACustomResultSetMapping.entity.Book;
import com.example.JPACustomResultSetMapping.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@EnableBatchProcessing
@Configuration
public class SpringBatchConfig {
    public static Logger logger = LoggerFactory.getLogger(SpringBatchConfig.class);

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<Book> bookItemReader() {
        JpaPagingItemReader<Book> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(10);

        CustomJpaNativeQueryProvider queryProvider = new CustomJpaNativeQueryProvider();
        reader.setQueryProvider(queryProvider);

        return reader;
    }

    @Bean
    public ItemProcessor<Book, Book> processor() {
        return new BookItemProcessor();
    }

    @Bean
    public ItemWriter<Book> writer() {
        return new BookItemWriter();
    }



    @Bean
    public Job processBookJob() {
        return jobBuilderFactory.get("processBookJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Book, Book>chunk(10)
                .reader(bookItemReader())
                .processor(processor())
                .processor(studentItemProcessor()) // Add StudentItemProcessor here
                .writer(writer())
                .writer(studentItemWriter())
                .allowStartIfComplete(true)
                .build();
    }
    @Bean
    public ItemProcessor<Book, Book> studentItemProcessor() {
        return new StudentItemProcessor();
    }

    @Bean
    public ItemWriter<Book> studentItemWriter(){
        return new StudentItemWriter();
    }
}
