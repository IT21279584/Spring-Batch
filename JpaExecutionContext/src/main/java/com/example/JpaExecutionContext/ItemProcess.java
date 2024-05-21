package com.example.JpaExecutionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ItemProcess implements ItemProcessor<String, String>, StepExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(ItemProcess.class);

    private StepExecution stepExecution;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public String process(String item) throws Exception {
        logger.info("Processing item: " + item);
        JobExecution jobExecution = stepExecution.getJobExecution();
        int processedCount = jobExecution.getExecutionContext().getInt("processedCount", 0);
        jobExecution.getExecutionContext().put("processedCount", processedCount + 1);
        return item;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}

