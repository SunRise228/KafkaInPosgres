package com.test.test.service;

import com.test.test.repository.CompanyRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CustomMetricService {

    @Autowired
    CompanyRepository companyRepository;

    public Supplier<Number> fetchCompanySize() {
        return () -> companyRepository.findAll().size();
    }

    public CustomMetricService(MeterRegistry registry) {
        Gauge.builder("company.size", fetchCompanySize())
                .tag("version", "v1")
                .description("Custom Metric")
                .register(registry);
    }

}
