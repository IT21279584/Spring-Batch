package com.example.JPACustomResultSetMapping.component;

import com.example.JPACustomResultSetMapping.entity.Book;
import org.springframework.batch.item.ItemProcessor;

public class BookItemProcessor implements ItemProcessor<Book, Book> {

    @Override
    public Book process(Book book) throws Exception {
        if (book.getStudent() != null) {
            book.getStudent().setFirstname(book.getStudent().getFirstname().toUpperCase());
        }
        return book;
    }
}
