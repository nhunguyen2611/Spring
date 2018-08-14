package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
	
	//Tim kiem cua EmployeeService.java
	List<Employee> findByNameContaining(String q);

	// Tim "id" de Edit
	Employee findById(int id);



}
