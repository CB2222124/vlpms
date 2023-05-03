package com.github.cb2222124.vlpms.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@Getter
@Setter
public class Registration {

    @Id
    private String registration;

    private String style;

    @JsonIgnore
    @OneToOne(mappedBy = "registration", orphanRemoval = true)
    private Listing listing;

    public Registration(String registration, String style) {
        this.registration = registration;
        this.style = style;
    }
}