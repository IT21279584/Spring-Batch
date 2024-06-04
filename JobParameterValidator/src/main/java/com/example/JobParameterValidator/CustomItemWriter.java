package com.example.JobParameterValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;

import java.util.List;


public class CustomItemWriter implements ItemWriter<String> {

    private static final Logger logger = LoggerFactory.getLogger(CustomItemWriter.class);

    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }


    @Override
    public void write(List<? extends String> list) throws Exception {
        ExecutionContext stepContext = stepExecution.getExecutionContext();
        logger.info("Current record count : " + stepContext.getInt("recordCount", 0));
    }
}
