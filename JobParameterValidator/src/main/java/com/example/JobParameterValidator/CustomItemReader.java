package com.example.JobParameterValidator;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomItemReader implements ItemReader<String> {

    private List<String> items;
    private int index = 0;

    private String filter;

    public CustomItemReader(List<String> items, @Value("#{jobParameters['filter']}") String filter) {
        this.filter = filter;
        this.items = items;
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(filter == null){
            filter = "item";
        }
        while (index < items.size()) {
            String item = items.get(index++);
            if (item.contains(filter)) {
                return item;
            }
        }
        return null;
    }
}
