package com.example.JPACustomResultSetMapping.component;

import com.example.JPACustomResultSetMapping.entity.Book;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import javax.persistence.EntityManagerFactory;

public class CustomJpaNativeQueryProvider extends JpaNativeQueryProvider<Book> {
    public CustomJpaNativeQueryProvider() {
        setSqlQuery("SELECT b.id, b.title, b.version, b.student_id, " +
                "s.id as student_id, s.version as student_version, s.firstname, s.lastname, s.email " +
                "FROM book b " +
                "JOIN student s ON b.student_id = s.id");
        setEntityClass(Book.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }
}
