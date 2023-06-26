package com.test.test.service;

import com.test.test.model.Company;
import com.test.test.model.Employee;
import com.test.test.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void editEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Transactional
    public Integer deleteTestEmployeeQuery() {
        return employeeRepository.deleteAllTestEmployee();
    }

    @Transactional
    public void raiseManager() {
        Random random = new Random();
        int value = 1 + random.nextInt(10);
        String managerName = "manager" + value;
        Optional<Employee> employee = employeeRepository.getExactManager(managerName);
        if (employee.isEmpty()) {
            throw new ApiException();
        }
        Employee employeeToEdit = employee.get();
        employeeToEdit.setSalary(employeeToEdit.getSalary() + 1);
        employeeRepository.save(employeeToEdit);
    }

    @Transactional
    public void setManager() {
        List<Employee> managerList = employeeRepository.getManager();
        for (var managerListIterator : managerList) {
            managerListIterator.setSalary(120);
            employeeRepository.save(managerListIterator);
        }
    }
}
