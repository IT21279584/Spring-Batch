package com.example.JobParameterValidator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class CustomJobParameterValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        String filter = jobParameters.getString("filter");
        if(filter == null || filter.isEmpty()){
            filter = "item";
        }
    }
}
