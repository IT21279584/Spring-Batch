package com.example.JobParameterValidator;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<String, String>, StepExecutionListener {

    private StepExecution stepExecution;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
    @Override
    public String process(String s) throws Exception {
        ExecutionContext stepContext = stepExecution.getExecutionContext();
        int recordCount = stepContext.getInt("recordCount", 0);
        stepContext.put("recordCount", recordCount + 1);
        return s;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
