package com.example.MultiStepsTasklet;


import org.springframework.batch.item.ItemProcessor;

public class StudentProcessor2 implements ItemProcessor<Student, NewStudent> {
    @Override
    public NewStudent process(Student student) throws Exception{

        NewStudent newStudent = new NewStudent();
        newStudent.setStudentID(student.getId());
        newStudent.setFirstname(student.getName() + " New Student");

        return newStudent;
    }
}