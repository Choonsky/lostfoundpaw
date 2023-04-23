package com.nemirovsky.lostfoundpaw.repository;

import com.nemirovsky.lostfoundpaw.model.Pet;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PetRepository extends ReactiveMongoRepository<Pet, String> {

    @Query("{id:'?0'}")
    Mono<Pet> findPetById(String id);

}