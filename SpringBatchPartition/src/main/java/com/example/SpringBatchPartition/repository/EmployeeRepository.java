package com.example.SpringBatchPartition.repository;

import com.example.SpringBatchPartition.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Query("SELECT DISTINCT e.department FROM Employee e")
    List<String> findDistinctDepartments();
}
