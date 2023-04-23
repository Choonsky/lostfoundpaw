package com.nemirovsky.lostfoundpaw.handler;

import com.nemirovsky.lostfoundpaw.model.Paw;
import com.nemirovsky.lostfoundpaw.service.PawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PawHandler {

    private Mono<String> lastMessage;

    private final PawService pawService;

    public Mono<ServerResponse> mainPage(ServerRequest request) {
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(pawService.getAllPaws(), 3);
        final Map<String, IReactiveDataDriverContextVariable> model =
                Collections.singletonMap("pets", reactiveDataDrivenMode);
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("index", model);
    }

    public Mono<ServerResponse> getAllPaws(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pawService.getAllPaws(), Paw.class);
    }

    public Mono<ServerResponse> getPawById(ServerRequest request) {
        final Map<String, Mono<Paw>> model =
                Collections.singletonMap("paw", pawService.findById(request.pathVariable("pawId")));
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("paw", model);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Paw> pet = request.bodyToMono(Paw.class);

        return pet
                .flatMap(p -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(pawService.createPaw(p), Paw.class)
                );
    }

    public Mono<ServerResponse> updatePawById(ServerRequest request) {
        String id = request.pathVariable("pawId");
        Mono<Paw> updatedPet = request.bodyToMono(Paw.class);

        return updatedPet
                .flatMap(p -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(pawService.updatePaw(id, p), Paw.class)
                );
    }

    public Mono<ServerResponse> deletePawById(ServerRequest request) {
        return pawService.deletePaw(request.pathVariable("pawId"))
                .flatMap(p -> ServerResponse.ok().body(p, Paw.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
