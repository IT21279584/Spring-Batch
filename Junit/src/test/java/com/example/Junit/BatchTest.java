package com.example.Junit;

import com.example.Junit.model.Employee;
import com.example.Junit.repository.EmployeeRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeClass
    public static void setupClass() {
        System.out.println("Before all tests");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("After all tests");
    }

    @Before
    public void setup() {
        employeeRepository.deleteAll();
        Employee emp1 = new Employee();
        emp1.setName("John");
        emp1.setDepartment("HR");


        employeeRepository.save(emp1);

    }

    @After
    public void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    public void testBatchJob() throws Exception {
        JobExecution jobExecution = jobLauncher.run(job, new org.springframework.batch.core.JobParameters());
        assertEquals("COMPLETED", jobExecution.getStatus().toString());


        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            assertEquals("HR", employee.getDepartment());
        }
    }
}
