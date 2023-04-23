package com.nemirovsky.lostfoundpaw.config;

import com.nemirovsky.lostfoundpaw.handler.PawHandler;
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
    RouterFunction<ServerResponse> routes(PawHandler handler) {
        return route(GET("/paws").and(accept(MediaType.APPLICATION_JSON)), handler::getAllPaws)
                .andRoute(GET("/").and(accept(MediaType.TEXT_HTML)), handler::mainPage)
                .andRoute(GET("/paw/{pawId}").and(accept(MediaType.TEXT_HTML)), handler::getPawById)
                .andRoute(POST("/paw").and(accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(PUT("/paw/{pawId}").and(contentType(MediaType.APPLICATION_JSON)), handler::updatePawById)
                .andRoute(DELETE("/paw/{userId}").and(accept(MediaType.APPLICATION_JSON)), handler::deletePawById);
    }
    @Bean
    public RouterFunction<ServerResponse> staticResourceRouter() {
        return RouterFunctions.resources("/**", new ClassPathResource("static/"));
    }
}
