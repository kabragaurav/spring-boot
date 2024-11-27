package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Employee;
import com.example.demo.services.EmployeeService;

/**
 * Controller is like a manager
 * It just accepts requests and delegates
 */
@RestController
public class EmployeeController {

    // dependency injection (DI)
    private final EmployeeService employeeService;

    /**
     * Ctor injection is preferred over @Autowired
     * WHY?
     * 
     * Readability - With @Autowired, dependencies are often hidden in fields, making the code harder to understand.
     * No NPE - With constructor injection, all required dependencies must be explicitly provided during object creation.
     * Faster - @Autowired relies on reflection to inject dependencies, which is slower.
     * Easier Unit Testing - Simple to test classes by manually providing mock.
     * S of SOLID - If a class has too many dependencies, it's a sign to refactor the class into smaller, more focused components.
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping("/employee")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("employee/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping("/employee")
    public String createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @DeleteMapping("/employee/{id}")
    public boolean deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }
    
    @PutMapping("employee/{id}")
    public String updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }
}
