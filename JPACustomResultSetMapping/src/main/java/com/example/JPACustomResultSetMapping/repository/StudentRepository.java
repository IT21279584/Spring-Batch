package com.example.JPACustomResultSetMapping.repository;


import com.example.JPACustomResultSetMapping.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
