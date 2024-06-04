package com.example.JobParameterValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobParameterValidatorApplication implements CommandLineRunner  {

	@Autowired
	private JobRunner jobRunner;

	public static void main(String[] args) {
		SpringApplication.run(JobParameterValidatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		jobRunner.runJob();
	}
}
