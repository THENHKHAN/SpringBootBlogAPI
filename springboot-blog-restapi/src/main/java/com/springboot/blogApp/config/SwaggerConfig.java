package com.springboot.blogApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Blog REST API Application By NHKHAN")
                        .version("1.0")
                        .termsOfService("Terms of Service")
                        .description("API documentation for Spring Boot Blog Application"));
    }
}
