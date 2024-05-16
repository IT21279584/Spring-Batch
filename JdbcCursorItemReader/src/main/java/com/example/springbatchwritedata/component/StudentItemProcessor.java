package com.example.springbatchwritedata.component;

import com.example.springbatchwritedata.entity.Student;
import org.springframework.batch.item.ItemProcessor;


public class StudentItemProcessor implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student student) throws Exception {
        return student;
    }
}
