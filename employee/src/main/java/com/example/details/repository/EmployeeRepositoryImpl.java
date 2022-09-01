package com.example.details.repository;


import com.example.details.domain.entiry.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Map<String, Employee> employeeMap;

    private EntityManager entityManager;
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Autowired
    public EmployeeRepositoryImpl(EntityManager entityManager) {
        employeeMap = new ConcurrentHashMap<>();
//        employeeMap = entityManager.createNativeQuery("Select id as i, name as n from search_api.employee", Tuple.class).getResultStream().collect(Collectors.toConcurrentMap());
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void putAllId() {
        List<Employee> temp = entityManager.createQuery("Select e from Employee e").getResultList();

        System.out.println(temp);

        for (Employee e : temp){
            employeeMap.put(e.getId(), new Employee(e.getId(), e.getName()));
        }
    }
    @Override
    public Employee getById(String id) {
        return employeeMap.get(id);
    }

    @Override
    public Collection<Employee> getAll() {
        return employeeMap.values();
    }

    @Override
    public String save(Employee employee) {
        String id = String.valueOf(idGenerator.getAndIncrement());
        employee.setId(id);
        employeeMap.put(id, employee);
        return id;
    }
}