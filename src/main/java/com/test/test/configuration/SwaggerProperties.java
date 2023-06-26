package com.test.test.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "swagger-properties")
public class SwaggerProperties {
    private String title;
    private String description;
    private String version;
    private String termsOfServiceUrl;
    private String licenseName;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    private String authServerUrl;
    private String realm;
}