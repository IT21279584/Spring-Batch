package com.example.JpaExecutionContext;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ItemRead implements ItemReader<String> {

    private final String filterParam;
    private int count = 0;

    public ItemRead(@Value("#{jobParameters['filterParam']}") String filterParam) {
        this.filterParam = filterParam;
    }

    @Override
    public String read() throws Exception {
        if (filterParam != null && !filterParam.isEmpty() && count < 10) {
            return "Item " + count++;
        }
        return null;
    }
}
