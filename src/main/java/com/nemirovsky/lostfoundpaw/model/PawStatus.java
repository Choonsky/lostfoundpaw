package com.nemirovsky.lostfoundpaw.model;

import lombok.Getter;

@Getter
public enum PetStatus {

    LOST("LOST"),
    FOUND ("FOUND"),
    NEITHER ("NEITHER");
    private final String text;

    PetStatus(String text) {
        this.text = text;
    }
}
