package com.example.springbatchexercise2.component;

import com.example.springbatchexercise2.config.SpringBatchConfig;
import com.example.springbatchexercise2.entity.Student;
import com.example.springbatchexercise2.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentItemWriter  implements ItemWriter<Student> {

    public static Logger logger = LoggerFactory.getLogger(SpringBatchConfig.class);

    @Autowired
    private  StudentRepository studentRepository;


    @Override
    public void write(List<? extends Student> students) throws Exception {
            for(Student student: students){
//                student.setFirstname(student.getFirstname().toUpperCase() + " Hello");
                studentRepository.save(student);
            }
    }
}
