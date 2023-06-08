package com.test.test.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
@Profile("!test")
@EnableJpaRepositories(basePackages = { "com.test.test.repository" })
public class PostgresConfiguration {

    @Bean
    public DataSource dataSource() throws IOException {
        DataSource postgresDataBase = DataSourceBuilder
                .create()
                .url("jdbc:postgresql://localhost:5433/postgres")
                .username("postgres")
                .password("postgres")
                .build();
        return postgresDataBase;
    }
}
