package com.example.edutech.OpenApiConfig;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI edutechOpenAPI() {
        return new OpenAPI()
        .info(new Info()
        .title("API's de Edutech")
        .version("v2.0")
        .description("Documentación de las API's REST para gestionar los microservicios de catálogo de cursos, usuarios y carrito de compras")
        );
    }
    
}
