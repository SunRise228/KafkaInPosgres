package com.test.test.service;

import com.test.test.model.Company;
import com.test.test.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public Optional<Company> findById(UUID id) {
        return companyRepository.findById(id);
    }

    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    public void editCompany(Company company) {
        companyRepository.save(company);
    }

    public void deleteCompany(Company company) {
        companyRepository.delete(company);
    }

}
