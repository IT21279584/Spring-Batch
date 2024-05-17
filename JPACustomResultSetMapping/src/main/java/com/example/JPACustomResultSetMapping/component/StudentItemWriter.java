package com.example.JPACustomResultSetMapping.component;

import com.example.JPACustomResultSetMapping.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class StudentItemWriter implements ItemWriter<Book> {
    public static Logger logger = LoggerFactory.getLogger(StudentItemProcessor.class);

    @Override
    public void write(List<? extends Book> items) throws Exception {
        for (Book book : items) {
            // Example: Just logging the book and student information
            logger.info("Writing Book: " + book.getTitle() + ", Student: " + book.getStudent().getFirstname() + " " + book.getStudent().getLastname());
        }
    }
}
