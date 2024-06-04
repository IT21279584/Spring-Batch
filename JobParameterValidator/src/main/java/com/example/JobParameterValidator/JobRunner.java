package com.example.JobParameterValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRunner {

    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    public void runJob() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("filter", "item")
                .toJobParameters();
        logger.info("Running job with parameters: {}", jobParameters);
        jobLauncher.run(job, jobParameters);
    }

}
