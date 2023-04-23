package com.nemirovsky.lostfoundpaw.service;

import com.nemirovsky.lostfoundpaw.model.Paw;
import com.nemirovsky.lostfoundpaw.repository.PawRepository;
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
public class PawService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final PawRepository pawRepository;

    public Mono<Paw> createPaw(Paw paw) {
        return pawRepository.save(paw);
    }

    public Flux<Paw> getAllPaws() {
        //To simulate big list of data, streaming with 1-second delay, use
        //return petRepository.findAll().delayElements(Duration.ofSeconds(1));
        return pawRepository.findAll();
    }

    public Mono<Paw> findById(String pawId) {
        return pawRepository.findById(pawId);
    }

    public Mono<Paw> updatePaw(String pawId, Paw paw) {
        return pawRepository.findById(pawId)
                .flatMap(p -> {
                    p.setName(paw.getName());
                    // TODO: add update other fields
                    return pawRepository.save(p);
                });
    }

    public Mono<Paw> deletePaw(String pawId) {
        return pawRepository.findById(pawId)
                .flatMap(paw -> pawRepository.delete(paw)
                        .then(Mono.just(paw)));
    }

    // Example with MongoTemplate query
    public Flux<Paw> fetchPaws(User user) {
        Query query = new Query()
                .with(Sort
                        .by(Collections.singletonList(Sort.Order.asc("created_time")))
                );
        query.addCriteria(Criteria
                .where("addedByUser")
                .regex(user.getUsername())
        );

        return reactiveMongoTemplate
                .find(query, Paw.class);
    }
}
