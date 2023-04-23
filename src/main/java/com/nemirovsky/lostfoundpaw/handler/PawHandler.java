package com.nemirovsky.lostfoundpaw.handler;

import com.nemirovsky.lostfoundpaw.model.Pet;
import com.nemirovsky.lostfoundpaw.service.PetService;
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
public class PetHandler {

    private Mono<String> lastMessage;

    private final PetService petService;

    public Mono<ServerResponse> mainPage(ServerRequest request) {
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(petService.getAllPets(), 3);
        final Map<String, IReactiveDataDriverContextVariable> model =
                Collections.singletonMap("pets", reactiveDataDrivenMode);
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("index", model);
    }

    public Mono<ServerResponse> getAllPets(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(petService.getAllPets(), Pet.class);
    }

    public Mono<ServerResponse> getPetById(ServerRequest request) {
        final Map<String, Mono<Pet>> model =
                Collections.singletonMap("pet", petService.findById(request.pathVariable("petId")));
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("pet", model);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Pet> pet = request.bodyToMono(Pet.class);

        return pet
                .flatMap(p -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(petService.createPet(p), Pet.class)
                );
    }

    public Mono<ServerResponse> updatePetById(ServerRequest request) {
        String id = request.pathVariable("petId");
        Mono<Pet> updatedPet = request.bodyToMono(Pet.class);

        return updatedPet
                .flatMap(p -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(petService.updatePet(id, p), Pet.class)
                );
    }

    public Mono<ServerResponse> deletePetById(ServerRequest request) {
        return petService.deletePet(request.pathVariable("petId"))
                .flatMap(p -> ServerResponse.ok().body(p, Pet.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
