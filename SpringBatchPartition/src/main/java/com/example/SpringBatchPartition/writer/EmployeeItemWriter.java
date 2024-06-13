package com.example.SpringBatchPartition.writer;

import com.example.SpringBatchPartition.model.Employee;
import com.example.SpringBatchPartition.repository.EmployeeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public class EmployeeItemWriter implements ItemWriter<Employee> {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeItemWriter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void write(List<? extends Employee> employees) {
        employeeRepository.saveAll(employees);
    }
}
