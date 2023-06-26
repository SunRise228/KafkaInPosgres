package com.test.test.repository;

import com.test.test.model.Company;
import com.test.test.model.Employee;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    String DELETE_TEST_EMPLOYEE = "DELETE FROM Employee e WHERE e.email LIKE '%@test.test'";
    String GET_MANAGERS = "SELECT e FROM Employee e WHERE e.name LIKE 'manager%'";

    String GET_MANAGER = "SELECT e FROM Employee e WHERE e.name = ?1";

    @Modifying
    @Query(value = DELETE_TEST_EMPLOYEE)
    Integer deleteAllTestEmployee();

    @Query(value = GET_MANAGERS)
    List<Employee> getManager();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = GET_MANAGER)
    Optional<Employee> getExactManager(String name);

}
