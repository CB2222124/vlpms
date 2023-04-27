package com.github.cb2222124.vlpms.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Listing {

    @Id
    @OneToOne
    private Registration registration;

    private int pricePence;
}
