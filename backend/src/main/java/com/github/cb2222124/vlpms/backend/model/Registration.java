package com.github.cb2222124.vlpms.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Registration {

    @Id
    private String registration;

    private String style;

    @OneToOne(mappedBy = "registration")
    private Listing listing;

}