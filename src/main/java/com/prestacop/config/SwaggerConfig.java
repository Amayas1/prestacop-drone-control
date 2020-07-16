package com.prestacop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.prestacop.controllers"))
                .paths(PathSelectors.regex("/kafka.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Prestacop Drone Control",
                "API Rest pour la collecte des logs envoyées par le drone",
                "API 0.0.1",
                "Terms of service",
                new Contact("Amayas Khelfoune", "https://fr.linkedin.com/in/amayas-khelfoune-83b790173", "amayaskhelfoune@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}
