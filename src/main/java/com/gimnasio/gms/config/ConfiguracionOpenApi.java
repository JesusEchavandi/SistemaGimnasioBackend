package com.gimnasio.gms.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionOpenApi {

    @Bean
    public OpenAPI openApi() {
        String esquema = "BearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema de Gestion de Gimnasio")
                        .version("v1")
                        .description("API REST para membresias, clases y asistencia"))
                .addSecurityItem(new SecurityRequirement().addList(esquema))
                .components(new Components()
                        .addSecuritySchemes(esquema,
                                new SecurityScheme()
                                        .name(esquema)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
