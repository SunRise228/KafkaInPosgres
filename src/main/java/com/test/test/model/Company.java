package com.test.test.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Table(name = "company")
public class Company {

    @Id
    private UUID id;
    private String country;
    private String name;
    private String tin;
    private Instant createTime;
    private Instant lastUpdateTime;

}