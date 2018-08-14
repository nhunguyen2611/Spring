package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class EmployeeService  {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	// In tat ca cac phan tu cua bang Employee
	public Iterable<Employee> getAllEmp(){
		return employeeRepository.findAll();
	}
	
	//Tim kiem theo ten
	public List<Employee> search(String q) {
        return employeeRepository.findByNameContaining(q);
    }
	
	//Tim id de Edit
	public Employee findOne(int id) {
        return employeeRepository.findById(id);
    }
	
	//Save 
	public void save(Employee contact) {
    	employeeRepository.save(contact);
    }
	
	// Xoa
	public void delete(int id) {
    	employeeRepository.deleteById(id);
    }
	
	
	

}
