package com.test.test.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private UUID companyId;
    private UUID supervisorId;
    private String name;
    private Integer salary;
    private String email;
    private Instant createTime;
    private Instant lastUpdateTime;

    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

}
