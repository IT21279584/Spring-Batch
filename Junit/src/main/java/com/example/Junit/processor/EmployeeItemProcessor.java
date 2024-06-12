package com.example.Junit.processor;

import com.example.Junit.model.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) throws Exception {
        // Example processing: Convert the employee's name to uppercase
        employee.setName(employee.getName().toUpperCase());
        return employee;
    }
}


