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
