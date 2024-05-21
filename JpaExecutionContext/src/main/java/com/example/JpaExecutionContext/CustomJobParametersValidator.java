package com.example.JpaExecutionContext;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

@Component
public class CustomJobParametersValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String filterParam = parameters.getString("filterParam");
        if (filterParam == null || filterParam.isEmpty()) {
            throw new JobParametersInvalidException("The filterParam parameter is required.");
        }
    }
}

