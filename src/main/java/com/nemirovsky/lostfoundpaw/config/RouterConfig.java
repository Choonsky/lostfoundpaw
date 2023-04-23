package com.nemirovsky.lostfoundpaw.config;

import com.nemirovsky.lostfoundpaw.handler.PetHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> routes(PetHandler handler) {
        return route(GET("/pets").and(accept(MediaType.APPLICATION_JSON)), handler::getAllPets)
                .andRoute(GET("/").and(accept(MediaType.TEXT_HTML)), handler::mainPage)
                .andRoute(GET("/pet/{petId}").and(accept(MediaType.TEXT_HTML)), handler::getPetById)
                .andRoute(POST("/pet").and(accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(PUT("/pet/{petId}").and(contentType(MediaType.APPLICATION_JSON)), handler::updatePetById)
                .andRoute(DELETE("/pet/{userId}").and(accept(MediaType.APPLICATION_JSON)), handler::deletePetById);
    }
    @Bean
    public RouterFunction<ServerResponse> staticResourceRouter() {
        return RouterFunctions.resources("/**", new ClassPathResource("static/"));
    }
}
