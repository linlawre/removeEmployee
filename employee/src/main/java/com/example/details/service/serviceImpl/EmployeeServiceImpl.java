package com.example.details.service.serviceImpl;

import com.example.details.domain.dto.EmployeeResponseDTO;
import com.example.details.domain.entiry.Employee;
import com.example.details.exception.ResourceNotFoundException;
import com.example.details.repository.EmployeeRepository;
import com.example.details.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeResponseDTO getById(String id) {
        Employee emp = employeeRepository.getById(id);
        if(emp == null) {
            //log this id , log this request info
            throw new ResourceNotFoundException(".....");
        }
        return new EmployeeResponseDTO(emp);
    }

    @Override
    public Collection<EmployeeResponseDTO> getAllEmp() {
        return employeeRepository.getAll()
                .stream()
                .map(e -> new EmployeeResponseDTO(e))
                .collect(Collectors.toList());
    }



    @Override
    public String save(Employee emp) {
        return employeeRepository.save(emp);
    }
}