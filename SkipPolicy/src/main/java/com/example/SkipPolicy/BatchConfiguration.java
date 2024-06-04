package com.example.SkipPolicy;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.SkipListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job job(Step step) {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<String, String>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
                .skip(NullPointerException.class)
                .skipLimit(5)
                .listener(skipListener())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemReader<String> reader() {
        return new ItemReader<String>() {
            private int count = 0;
            @Override
            public String read() {
                if (count < 25) {
                    count++;
                    return "item " + count;
                } else {
                    return null;
                }
            }
        };
    }

    @Bean
    public ItemProcessor<String, String> processor() {
        return item -> {
            if (Math.random() > 0.8) { // Randomly causing a NullPointerException
                throw new NullPointerException("NullPointerException");
            }
            return item;
        };
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> items.forEach(System.out::println);
    }

    @Bean
    public SkipListenerSupport<String, String> skipListener() {
        return new SkipListenerSupport<String, String>() {
            @Override
            public void onSkipInProcess(String item, Throwable t) {
                System.out.println("Skipping item " + item + " due to exception: " + t.getMessage());
            }
        };
    }
}
