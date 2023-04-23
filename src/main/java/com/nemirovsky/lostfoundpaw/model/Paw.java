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
@Document(value = "pets")
public class Pet {
    @Transient
    public static final String SEQUENCE_NAME = "pets_sequence";
    @Id
    private String id;
    private User addedByUser;
    private User ownedByUser;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String name;
    private PetSpecies species;
    private String breed;
    private LocalDate birthDate;
    private Location location;
    private String[] imgSources;
    private int mainImageIndex = 0;
    private PetStatus petStatus;

    private Pet(PetBuilder builder) {
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
        this.petStatus = builder.petStatus;
    }

    // Builder inner class

    public static class PetBuilder{
        private String id;
        private User addedByUser;
        private User ownedByUser;
        private LocalDateTime createdTime;
        private LocalDateTime updatedTime;
        private String name;
        private PetSpecies species;
        private String breed;
        private LocalDate birthDate;
        private Location location;
        private String[] imgSources;
        private int mainImageIndex = 0;
        private PetStatus petStatus;

        public PetBuilder() {}

        public PetBuilder id(String id) {
            this.id = id;
            return this;
        }
        public PetBuilder addedByUser(User user) {
            this.addedByUser = user;
            return this;
        }
        public PetBuilder ownedByUser(User user) {
            this.ownedByUser = user;
            return this;
        }
        public PetBuilder createdTime(LocalDateTime time) {
            this.createdTime = time;
            return this;
        }
        public PetBuilder updatedTime(LocalDateTime time) {
            this.updatedTime = time;
            return this;
        }
        public PetBuilder name(String name) {
            this.name = name;
            return this;
        }
        public PetBuilder species(PetSpecies species) {
            this.species = species;
            return this;
        }
        public PetBuilder breed(String breed) {
            this.breed = breed;
            return this;
        }
        public PetBuilder birthDate(LocalDate date) {
            this.birthDate = date;
            return this;
        }
        public PetBuilder location(Location location) {
            this.location = location;
            return this;
        }
        public PetBuilder imgSources(String[] imgSources) {
            this.imgSources = imgSources;
            return this;
        }
        public PetBuilder mainImageIndex(int id) {
            this.mainImageIndex = id;
            return this;
        }
        public PetBuilder petStatus(PetStatus status) {
            this.petStatus = status;
            return this;
        }
        public Pet build() {
            return new Pet(this);
        }
    }
}