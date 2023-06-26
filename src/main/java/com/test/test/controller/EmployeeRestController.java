package com.test.test.controller;

import com.test.test.exception.apiexception.ApiRequestException;
import com.test.test.model.Company;
import com.test.test.model.Employee;
import com.test.test.model.EmployeeRole;
import com.test.test.publisher.eventpublisher.PostgresEventPublisher;
import com.test.test.service.EmployeeService;
import com.test.test.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/employee")
@Tag(name = "Employee")
public class EmployeeRestController {

    @Autowired
    private PostgresEventPublisher postgresEventPublisher;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Operation(summary = "Найти работника", description = "Поиск работника по его UUID")
    @GetMapping
    public Employee getEmployeeById(@RequestParam UUID id) {
        Optional<Employee> company = employeeService.findById(id);
        if(company.isEmpty()) {
            throw new ApiException("Employee not found");
        }
        postgresEventPublisher.publishEvent("Found employee");
        return company.get();
    }

    @Operation(summary = "Добавить работника", description = "Добавить работника по полям: UUID компании, имени, зарплате, почте, роли")
    @PostMapping
    public ResponseEntity<HttpStatus> addEmployee(@RequestBody Employee employee) {
        if(!validationService.isValidEmail(employee.getEmail())) {
            postgresEventPublisher.publishEvent("Cant add employee");
            throw new ApiRequestException("Cant add employee");
        }
        Date date = Date.from(Instant.now());
        String insertQuery = "insert into employee (id, company_id, supervisor_id, name, " +
                "salary, email, role, create_time, last_update_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertQuery, UUID.randomUUID(), employee.getCompanyId(), employee.getSupervisorId(), employee.getName(),
                employee.getSalary(), employee.getEmail(), employee.getRole().toString(), date, date);
        postgresEventPublisher.publishEvent("Added new employee");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Редактировать работника", description = "Редактировать работника по зарплате или изменить ему руководителя")
    @PutMapping
    @ResponseBody
    public ResponseEntity<HttpStatus> editEmployeeSalaryOrSupervisor(@RequestBody Employee employee) {

        Optional<Employee> foundEmployee = employeeService.findById(employee.getId());

        if(foundEmployee.isEmpty()) {
            postgresEventPublisher.publishEvent("Cant edit employee");
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        foundEmployee.ifPresent(
                employee1 -> {
                    if(employee.getSalary() != null) {
                        employee1.setSalary(employee.getSalary());
                    }
                    if(employee.getSupervisorId() != null) {
                        employee1.setSupervisorId(employee.getSupervisorId());
                    }
                    employeeService.editEmployee(employee1);
                }
        );
        postgresEventPublisher.publishEvent("Edited employee");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Удалить работника", description = "Удалить работника по UUID")
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteEmployee(@RequestParam UUID id) {
        Optional<Employee> employee = employeeService.findById(id);
        if(employee.isEmpty()) {
            postgresEventPublisher.publishEvent("Cant delete employee");
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        employeeService.deleteEmployee(employee.get());
        postgresEventPublisher.publishEvent("Deleted employee");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Удаление тестовых работников", description = "Удаление всех работников, созданных в Jmeter")
    @DeleteMapping("/test")
    public ResponseEntity<HttpStatus> deleteTestEmployee() {
        Integer deletedCount = employeeService.deleteTestEmployeeQuery();
        log.info("Deleted " + deletedCount + " employee");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/manager/raise")
    public ResponseEntity<HttpStatus> giveManagersRaiseUp() throws InterruptedException {
        employeeService.raiseManager();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/manager/set")
    public ResponseEntity<HttpStatus> giveManagersNormal() {
        employeeService.setManager();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
