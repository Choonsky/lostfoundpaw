package com.nemirovsky.lostfoundpaw.repository;

import com.nemirovsky.lostfoundpaw.model.Paw;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PawRepository extends ReactiveMongoRepository<Paw, String> {

    @Query("{id:'?0'}")
    Mono<Paw> findPawById(String id);

}