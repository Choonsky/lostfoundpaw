package com.nemirovsky.lostfoundpaw.model;

import lombok.Getter;

@Getter
public enum PawStatus {

    LOST("LOST"),
    FOUND ("FOUND"),
    NEITHER ("NEITHER");
    private final String text;

    PawStatus(String text) {
        this.text = text;
    }
}
