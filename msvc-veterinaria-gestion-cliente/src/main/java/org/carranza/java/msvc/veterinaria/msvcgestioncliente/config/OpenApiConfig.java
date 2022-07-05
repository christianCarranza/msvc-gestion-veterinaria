package org.carranza.java.msvc.veterinaria.msvcgestioncliente.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("MSVC-GESTION-CLIENTE")
                        .description("Sistema de gestion veterianaria")
                        .contact(new Contact().email("carranzachristian61@gmail.com"))
                        .version("1.0.0")
                );
    }
}
