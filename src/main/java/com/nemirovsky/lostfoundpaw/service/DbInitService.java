package com.nemirovsky.lostfoundpaw.service;

import com.nemirovsky.lostfoundpaw.model.Location;
import com.nemirovsky.lostfoundpaw.model.Paw;
import com.nemirovsky.lostfoundpaw.model.PawSpecies;
import com.nemirovsky.lostfoundpaw.model.PawStatus;
import com.nemirovsky.lostfoundpaw.repository.PawRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DbInitService {

    private final PawRepository pets;

    private final SequenceGeneratorService sequenceGeneratorService;

    public DbInitService(SequenceGeneratorService sequenceGeneratorService, PawRepository pets) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.pets = pets;
    }

    public void setup() {
        log.info("Start DB populating with test pets...");
        String nextId = sequenceGeneratorService.generateSequence(Paw.SEQUENCE_NAME);
        Paw p1 = new Paw.PawBuilder()
                .id("P10001")
                .name("Alpha")
                .breed("Husky")
                .species((PawSpecies.DOG))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .pawStatus(PawStatus.FOUND)
                .build();
        Paw p2 = new Paw.PawBuilder()
                .id("P10002")
                .name("Bravo")
                .breed("Persian")
                .species((PawSpecies.CAT))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .pawStatus(PawStatus.FOUND)
                .build();
        Paw p3 = new Paw.PawBuilder()
                .id("P10003")
                .name("Charlie")
                .breed(null)
                .species((PawSpecies.RABBIT))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .pawStatus(PawStatus.LOST)
                .build();
        Paw p4 = new Paw.PawBuilder()
                .id("P10004")
                .name("Doodle")
                .breed("Simple hamster")
                .species((PawSpecies.HAMSTER))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .pawStatus(PawStatus.LOST)
                .build();
        Paw p5 = new Paw.PawBuilder()
                .id("P10005")
                .name("Eugeniya")
                .breed("Golden fish")
                .species((PawSpecies.OTHER))
                .addedByUser(null)
                .createdTime(LocalDateTime.now())
                .location(new Location(22.333, 33.222, 1.0))
                .imgSources(new String[]{nextId + "_A.jpg", nextId + "_B.jpg", nextId + "_C.jpg"})
                .pawStatus(PawStatus.NEITHER)
                .build();
        pets.deleteAll()
                .thenMany(Flux.just(p1, p2, p3, p4, p5)
                        .flatMap(this.pets::save))
                .log()
                .subscribe(null, null, () -> log.info("...done DB populating with test pets."));
    }

}
