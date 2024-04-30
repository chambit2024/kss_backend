package com.emptyseat.kss.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "\uD83D\uDCDA KSS API Documentation",
                description = "KSS API 명세서입니다.",
                version = "v1"
        ))
@Configuration
public class SwaggerConfig {
}
