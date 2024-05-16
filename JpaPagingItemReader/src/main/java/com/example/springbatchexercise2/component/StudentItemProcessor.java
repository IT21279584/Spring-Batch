package com.example.springbatchexercise2.component;


import com.example.springbatchexercise2.entity.Student;
import com.example.springbatchexercise2.repository.StudentRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {

    @Override
    public Student process(Student student) throws Exception {
        student.setFirstname(student.getFirstname().toUpperCase());
        return student;
    }
}
