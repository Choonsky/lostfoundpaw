package com.nemirovsky.lostfoundpaw.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "paws")
public class Paw {
    @Transient
    public static final String SEQUENCE_NAME = "paws_sequence";
    @Id
    private String id;
    private User addedByUser;
    private User ownedByUser;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String name;
    private PawSpecies species;
    private String breed;
    private LocalDate birthDate;
    private Location location;
    private String[] imgSources;
    private int mainImageIndex = 0;
    private PawStatus pawStatus;

    private Paw(PawBuilder builder) {
        this.id = builder.id;
        this.addedByUser = builder.addedByUser;
        this.ownedByUser = builder.ownedByUser;
        this.createdTime = builder.createdTime;
        this.updatedTime = builder.updatedTime;
        this.name = builder.name;
        this.species = builder.species;
        this.breed = builder.breed;
        this.birthDate = builder.birthDate;
        this.location = builder.location;
        this.imgSources = builder.imgSources;
        this.mainImageIndex = builder.mainImageIndex;
        this.pawStatus = builder.pawStatus;
    }

    // Builder inner class

    public static class PawBuilder{
        private String id;
        private User addedByUser;
        private User ownedByUser;
        private LocalDateTime createdTime;
        private LocalDateTime updatedTime;
        private String name;
        private PawSpecies species;
        private String breed;
        private LocalDate birthDate;
        private Location location;
        private String[] imgSources;
        private int mainImageIndex = 0;
        private PawStatus pawStatus;

        public PawBuilder() {}

        public PawBuilder id(String id) {
            this.id = id;
            return this;
        }
        public PawBuilder addedByUser(User user) {
            this.addedByUser = user;
            return this;
        }
        public PawBuilder ownedByUser(User user) {
            this.ownedByUser = user;
            return this;
        }
        public PawBuilder createdTime(LocalDateTime time) {
            this.createdTime = time;
            return this;
        }
        public PawBuilder updatedTime(LocalDateTime time) {
            this.updatedTime = time;
            return this;
        }
        public PawBuilder name(String name) {
            this.name = name;
            return this;
        }
        public PawBuilder species(PawSpecies species) {
            this.species = species;
            return this;
        }
        public PawBuilder breed(String breed) {
            this.breed = breed;
            return this;
        }
        public PawBuilder birthDate(LocalDate date) {
            this.birthDate = date;
            return this;
        }
        public PawBuilder location(Location location) {
            this.location = location;
            return this;
        }
        public PawBuilder imgSources(String[] imgSources) {
            this.imgSources = imgSources;
            return this;
        }
        public PawBuilder mainImageIndex(int id) {
            this.mainImageIndex = id;
            return this;
        }
        public PawBuilder pawStatus(PawStatus status) {
            this.pawStatus = status;
            return this;
        }
        public Paw build() {
            return new Paw(this);
        }
    }
}