package com.nemirovsky.lostfoundpaw.service;

import com.nemirovsky.lostfoundpaw.model.Pet;
import com.nemirovsky.lostfoundpaw.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final PetRepository petRepository;

    public Mono<Pet> createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Flux<Pet> getAllPets() {
        //To simulate big list of data, streaming with 1-second delay, use
        //return petRepository.findAll().delayElements(Duration.ofSeconds(1));
        return petRepository.findAll();
    }

    public Mono<Pet> findById(String petId) {
        return petRepository.findById(petId);
    }

    public Mono<Pet> updatePet(String petId, Pet pet) {
        return petRepository.findById(petId)
                .flatMap(p -> {
                    p.setName(pet.getName());
                    // TODO: add update other fields
                    return petRepository.save(p);
                });
    }

    public Mono<Pet> deletePet(String petId) {
        return petRepository.findById(petId)
                .flatMap(pet -> petRepository.delete(pet)
                        .then(Mono.just(pet)));
    }

    // Example with MongoTemplate query
    public Flux<Pet> fetchPets(User user) {
        Query query = new Query()
                .with(Sort
                        .by(Collections.singletonList(Sort.Order.asc("created_time")))
                );
        query.addCriteria(Criteria
                .where("addedByUser")
                .regex(user.getUsername())
        );

        return reactiveMongoTemplate
                .find(query, Pet.class);
    }
}
