package com.example.SpringBatchPartition.processor;


import com.example.SpringBatchPartition.model.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) {
        // Process the employee data (e.g., capitalize names)
        employee.setName(employee.getName().toUpperCase());
        return employee;
    }
}
