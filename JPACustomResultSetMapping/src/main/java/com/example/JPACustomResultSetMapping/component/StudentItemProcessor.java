package com.example.JPACustomResultSetMapping.component;

import com.example.JPACustomResultSetMapping.config.SpringBatchConfig;
import com.example.JPACustomResultSetMapping.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class StudentItemProcessor implements ItemProcessor<Book, Book> {
    public static Logger logger = LoggerFactory.getLogger(StudentItemProcessor.class);
    @Override
    public Book process(Book book) throws Exception {
        logger.info("Processing Book: " + book.getTitle() + ", Student: " + book.getStudent().getFirstname() + " " + book.getStudent().getLastname());
        return book;
    }
}
