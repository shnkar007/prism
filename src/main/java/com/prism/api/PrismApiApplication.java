package com.prism.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "PRISM API",
        version = "2.0.0",
        description = "AI-Powered Requirement Analyzer REST API",
        contact = @Contact(
            name = "PRISM Team",
            email = "team@prismcli.com",
            url = "https://prismcli.com"
        )
    )
)
public class PrismApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrismApiApplication.class, args);
    }
}