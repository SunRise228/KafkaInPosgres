package com.test.test.controller;

import com.test.test.model.Company;
import com.test.test.model.Employee;
import com.test.test.model.EmployeeRole;
import com.test.test.publisher.eventpublisher.PostgresEventPublisher;
import com.test.test.service.CompanyService;
import com.test.test.service.EmployeeService;
import com.test.test.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/company")
@Tag(name = "Company")
public class CompanyRestController {

    @Autowired
    private PostgresEventPublisher postgresEventPublisher;

    @Autowired
    private CompanyService companyService;

    @Operation(summary = "Найти компанию", description = "Найти компанию по UUID")
    @GetMapping
    public Company getCompanyById(@RequestParam UUID id) {
        Optional<Company> company = companyService.findById(id);
        if(company.isEmpty()) {
            throw new ApiException("Company not found");
        }
        postgresEventPublisher.publishEvent("Found company");
        return company.get();
    }

    @Operation(summary = "Добавить компанию", description = "Добавить компанию по полям: ")
    @PostMapping
    public ResponseEntity<HttpStatus> addCompany(@RequestBody Company company) {
        Company companyToAdd = Company.builder()
                .id(UUID.randomUUID())
                .country(company.getCountry())
                .name(company.getName())
                .tin(company.getTin())
                .createTime(Instant.now())
                .lastUpdateTime(Instant.now())
                .build();
        companyService.addCompany(companyToAdd);
        postgresEventPublisher.publishEvent("Added new company");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Редактировать компанию", description = "Редактировать компанию по имени или tin")
    @PutMapping
    public ResponseEntity<HttpStatus> editCompanyNameOrTin(@RequestBody Company company) {

        Optional<Company> foundCompany = companyService.findById(company.getId());

        if(foundCompany.isEmpty()) {
            postgresEventPublisher.publishEvent("Cant edit company");
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        foundCompany.ifPresent(
                company1 -> {
                    if (company.getName() != null) {
                        company1.setName(company.getName());
                    }
                    if (company.getTin() != null) {
                        company1.setTin(company.getTin());
                    }
                    company1.setLastUpdateTime(Instant.now());
                    companyService.editCompany(company1);
                }
        );
        postgresEventPublisher.publishEvent("Edited company");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Удалить компанию", description = "Удалить компанию по UUID")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCompany(@RequestParam UUID id) {
        Optional<Company> company = companyService.findById(id);
        if(company.isEmpty()) {
            postgresEventPublisher.publishEvent("Cant delete company");
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        companyService.deleteCompany(company.get());
        postgresEventPublisher.publishEvent("Deleted company");
        return ResponseEntity.ok(HttpStatus.OK);
    }

}