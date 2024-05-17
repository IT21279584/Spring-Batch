package com.example.JPACustomResultSetMapping.component;

import com.example.JPACustomResultSetMapping.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BookItemWriter implements ItemWriter<Book> {

    public static Logger logger = LoggerFactory.getLogger(BookItemWriter.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void write(List<? extends Book> items) throws Exception {
        for (Book book : items) {
            logger.info("Writing book: {}", book.getTitle());
            entityManager.merge(book);
        }
    }
}
