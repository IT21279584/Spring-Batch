package com.example.JPACustomResultSetMapping.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
@SqlResultSetMapping(
        name = "BookStudentMapping",
        entities = {
                @EntityResult(
                        entityClass = Book.class,
                        fields = {
                                @FieldResult(name = "id", column = "id"),
                                @FieldResult(name = "title", column = "title"),
                                @FieldResult(name = "version", column = "version"),
                                @FieldResult(name = "student", column = "student_id")
                        }),
                @EntityResult(
                        entityClass = Student.class,
                        fields = {
                                @FieldResult(name = "id", column = "student_id"),
                                @FieldResult(name = "version", column = "student_version"),
                                @FieldResult(name = "firstname", column = "firstname"),
                                @FieldResult(name = "lastname", column = "lastname"),
                                @FieldResult(name = "email", column = "email")
                        })
        }
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int version;
    private String title;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}

