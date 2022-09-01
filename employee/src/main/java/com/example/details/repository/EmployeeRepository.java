package com.example.details.repository;

import com.example.details.domain.entiry.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EmployeeRepository {
    Employee getById(String id);
    Collection<Employee> getAll();
    String save(Employee employee);

}