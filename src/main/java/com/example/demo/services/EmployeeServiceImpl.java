package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.models.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public String createEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "Save success!";
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(Long id) {
        Optional<Employee> matchEmployeeOptional = employeeRepository.findById(id);
        if (!matchEmployeeOptional.isPresent()) {
            return null;
        }
        return matchEmployeeOptional.get();
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Employee matchedEmployee = getAllEmployees().stream()
            .filter(employee -> employee.getId().equals(id))
            .findFirst()
            .orElse(null);
        employeeRepository.delete(matchedEmployee);
        return true;
    }

    @Override
    public String updateEmployee(Long id, Employee employee) {
        Optional<Employee> matchEmployeeOptional = employeeRepository.findById(id);
        if (!matchEmployeeOptional.isPresent()) {
            return "Does not exist";
        }
        Employee matchEmployee = matchEmployeeOptional.get();
        matchEmployee.setEmail(employee.getEmail());
        matchEmployee.setName(employee.getName());
        employeeRepository.save(matchEmployee);
        return "Update success!";
    }
    
}
