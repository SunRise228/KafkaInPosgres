package com.test.test.configuration;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
@Profile("test")
@EnableJpaRepositories(basePackages = { "com.test.test.repository" })
public class TestConfiguration {

    @Primary
    @Bean
    public DataSource dataSource() throws IOException {
        DataSource postgresDataBase = EmbeddedPostgres.builder()
                .setCleanDataDirectory(true)
                .start()
                .getPostgresDatabase();
        return postgresDataBase;
    }

}
