package com.example.JpaExecutionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


public class JobListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(JobListener.class);
    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().put("processedCount", 0);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Integer processedCount = jobExecution.getExecutionContext().getInt("processedCount");
        logger.info("Total processed count: " + processedCount);
    }
}

