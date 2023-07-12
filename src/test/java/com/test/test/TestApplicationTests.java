package com.test.test;

import com.test.test.model.Company;
import com.test.test.model.Employee;
import com.test.test.model.EmployeeRole;
import com.test.test.repository.CompanyRepository;
import com.test.test.repository.EmployeeRepository;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void embeddedDbTest() {

		Company addedMessage =  Company.builder()
				.id(UUID.fromString("8a39ebbc-dc72-4848-b20e-5490a1ea0638"))
				.country("test3")
				.name("test3")
				.tin("test3")
				.createTime(Instant.now())
				.lastUpdateTime(Instant.now())
				.build();

		companyRepository.save(addedMessage);

		Optional<Company> entityExist = companyRepository.findById(addedMessage.getId());
		Assertions.assertTrue(entityExist.isPresent());

	}

	@Test
	public void flywayInsertTest() {
		boolean foundAllInserts = companyRepository.findById(UUID.fromString("1cdf735a-37b5-5eca-9308-c48c42236264")).isPresent();
		Assertions.assertTrue(foundAllInserts);
	}

	@Test
	public void restTemplate() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjEiLCJpYXQiOjE2ODc3ODQzNDAsImV4cCI6MTY4Nzg3MDc0MH0.rWE88qim15qSXq7SlKFoH6WOv8oXpc_Z5Y3bcQ-Nv5E");


		JSONObject parameters = new JSONObject();
		parameters.put("companyId", "feeb1473-bc49-56ad-839a-e0435a2d98c8");
		parameters.put("name", "Dar2");
		parameters.put("salary", 120);
		parameters.put("email", "dar2@softclub.by");
		parameters.put("role", "EMPLOYEE");


		RequestEntity requestEntity = new RequestEntity(parameters, headers, HttpMethod.POST, URI.create("https://localhost:8888/employee"));
		ResponseEntity<HttpStatus> responseEntity = restTemplate.exchange(requestEntity, HttpStatus.class);

		Assertions.assertNotNull(responseEntity);
	}

}