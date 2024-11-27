package com.example.demo.services;

import java.util.List;

import com.example.demo.models.Employee;

public interface EmployeeService {
    // POST
    String createEmployee(Employee employee);
    // GET
    List<Employee> getAllEmployees();
    Employee getEmployee(Long id);
    // DELETE
    boolean deleteEmployee(Long id);
    // UPDATE
    String updateEmployee(Long id, Employee employee);
}
