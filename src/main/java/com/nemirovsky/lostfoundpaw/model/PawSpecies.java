package com.nemirovsky.lostfoundpaw.model;

import lombok.Getter;

@Getter
public enum PawSpecies {

    CAT("Cat"),
    DOG ("Dog"),
    RABBIT ("Rabbit or hare"),
    HAMSTER ("Hamster or something like"),
    TURTLE ("Turtle of a kind"),
    OTHER ("Other animal");
    private final String text;

    PawSpecies(String text) {
        this.text = text;
    }
}
