
package com.example.SpringBatchPartition.reader;

import com.example.SpringBatchPartition.model.Employee;
import com.example.SpringBatchPartition.repository.EmployeeRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

public class EmployeeItemReader implements ItemReader<Employee> {

    private final EmployeeRepository employeeRepository;
    private Iterator<Employee> employeeIterator;

    @Autowired
    public EmployeeItemReader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee read() {
        if (employeeIterator == null || !employeeIterator.hasNext()) {
            initializeIterator();
        }
        return employeeIterator.hasNext() ? employeeIterator.next() : null;
    }

    private void initializeIterator() {
        Iterable<Employee> employees = employeeRepository.findAll();
        employeeIterator = employees.iterator();
    }
}
