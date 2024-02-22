package com.example.Employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Employee {

	public static void main(String[] args) {

		SpringApplication.run(Employee.class, args);
	}
}
