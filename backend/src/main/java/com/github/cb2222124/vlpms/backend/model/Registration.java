package com.github.cb2222124.vlpms.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Registration {

    @Id
    private String registration;
    private String style;

    public Registration() {

    }

    public Registration(String registration, String style) {
        this.registration = registration;
        this.style = style;
    }
}
