package com.example.details.service;


import com.example.details.domain.dto.EmployeeResponseDTO;
import com.example.details.domain.entiry.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface EmployeeService {
    EmployeeResponseDTO getById(String id);
    Collection<EmployeeResponseDTO > getAllEmp();
    String save(Employee emp);
}