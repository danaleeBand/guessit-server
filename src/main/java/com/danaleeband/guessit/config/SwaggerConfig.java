package com.danaleeband.guessit.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Guessit API",
        version = "v1",
        description = "API documentation for Gueesit!"
    )
)
public class SwaggerConfig {

}
