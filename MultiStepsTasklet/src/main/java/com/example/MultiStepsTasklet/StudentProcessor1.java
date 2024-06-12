package com.example.MultiStepsTasklet;

import org.springframework.batch.item.ItemProcessor;

public class StudentProcessor1 implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student student) throws Exception{

        student.setName(student.getName() + " Old Student");
        return student;
    }
}


