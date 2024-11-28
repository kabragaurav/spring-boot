# Employee Management App Using Spring Boot & MySQL

A simple application to expose CRUD APIs to do employee management using Spring Boot and MySQL.

# Demo:
![](./assets/SpringBootAppDemo.mov)

# Setup
Clone this repo which was template from Spring Initializr.
Also setup MySQL and MySQL Workbench for easier DB interaction.

Use the script in `sql` directory and run in the workbench. It will create Database and show details of tables.

Also install Postman and Spring Boot Dashboard extensions in VSCode.

Use collection under `postman` to run CRUD APIs.

# Code

### application.properties

```bash
spring.application.name=EmployeeManagement

# Tomcat
server.port=9090

# DB details
spring.datasource.url=jdbc:mysql://localhost:3306/gauravkabra
spring.datasource.username=root
spring.datasource.password=12345678

spring.datasource.driver-class-name=com.mysql.jdbc.Driver
# Create table if does not exist
spring.jpa.hibernate.ddl-auto=update

#logs
spring.jpa.show-sql=true
```

### Main

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
```

### Models

```java
package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data               // @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Entity
@Table              // Since not providing name, it will be class name. E.g. HeyMan => hey_man
public class Employee {
    @Id                     // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // Auto generate. If we delete a record, its ID is not used again
    private Long id;
    private String name;
    private String email;
}
```

### Controller

```java
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
```

### Service

```java
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
```

```java
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
```

### DAO (Repository)

```java
package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {     // class, PK
    // CRUD methods are auto imported
    // so no need to create
    // just mention this class in service class
}
```

# Common Errors Encountered
1. 
```
{"timestamp":"2024-11-27T15:25:29.003+00:00","status":415,"error":"Unsupported Media Type","path":"/employee"}
```

Happens when the request's Content-Type is either missing or is not supported by the endpoint, especially for POST or PUT requests.

For a JSON payload (which is common for REST APIs), the header should be:

```
Content-Type: application/json
```
2. 
```
Field employeeService in com.example.demo.controller.EmployeeController required a bean of type 'com.example.demo.services.EmployeeService' that could not be found.

The injection point has the following annotations:
        - @org.springframework.beans.factory.annotation.Autowired(required=true)
```

Ensure that EmployeeServiceImpl class is annotated with @Service. Without this annotation, Spring won't recognize it as a bean.
