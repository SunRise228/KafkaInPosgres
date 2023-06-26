package com.test.test.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class SwaggerConfiguration {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI customOpenAPI() {

        Server server = new Server();
        server.setUrl("https://localhost:8888");
        server.setDescription("Server URL on local machine");
        return new OpenAPI()
                .servers(List.of(server))
                .info(
                        new Info()
                                .title(swaggerProperties.getTitle())
                                .description(swaggerProperties.getDescription())
                                .version(swaggerProperties.getVersion())
                                .termsOfService(swaggerProperties.getTermsOfServiceUrl())
                                .contact(new Contact()
                                        .name(swaggerProperties.getContactName())
                                        .email(swaggerProperties.getContactEmail())
                                        .url(swaggerProperties.getContactUrl()))
                                .license(new License()
                                        .name(swaggerProperties.getLicenseName())
                                        .url(""))
                );
    }

}