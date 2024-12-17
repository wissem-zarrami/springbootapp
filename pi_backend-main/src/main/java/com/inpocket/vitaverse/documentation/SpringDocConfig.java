/*package com.inpocket.vitaverse.Config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(infoAPI());
    }
    public Info infoAPI() {
        return new Info().title("SpringDoc-VitaVerse") .description("Backend") .contact(contactAPI());
    }
    public Contact contactAPI() {
        return new Contact().name("INPOCKET").email("VITAVERSE@INPOCKET.tn").url("https://github.com/MedAzizGhanmi7/pi_backend");
    }
/*
    @Bean
    public GroupedOpenApi productPublicApi() {return GroupedOpenApi.builder().group("Only Bloc ManagementAPI").pathsToMatch("api/blocs/**").pathsToExclude("**").build();

    }*/

