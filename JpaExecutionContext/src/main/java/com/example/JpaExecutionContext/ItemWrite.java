package com.example.JpaExecutionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemWrite implements ItemWriter<String> {

    private static final Logger logger = LoggerFactory.getLogger(ItemWrite.class);
    @Override
    public void write(List<? extends String> chunk) throws Exception {
        for (String item : chunk) {
            logger.info("Writing item: " + item);
        }
    }
}
