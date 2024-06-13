package com.example.SpringBatchPartition;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.StepExecutionAggregator;

import java.util.Collection;

public class DepartmentStepExecutionAggregator implements StepExecutionAggregator {

    @Override
    public void aggregate(StepExecution result, Collection<StepExecution> executions) {
        for (StepExecution stepExecution : executions) {
            result.setReadCount(result.getReadCount() + stepExecution.getReadCount());
            result.setWriteCount(result.getWriteCount() + stepExecution.getWriteCount());
            result.setCommitCount(result.getCommitCount() + stepExecution.getCommitCount());
            result.setRollbackCount(result.getRollbackCount() + stepExecution.getRollbackCount());
        }
    }
}