package com.github.cb2222124.vlpms.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@Getter
@Setter
public class Listing {

    @Id
    private String id;

    @JsonBackReference
    @MapsId
    @JoinColumn(name = "registration")
    @OneToOne(fetch = FetchType.LAZY)
    private Registration registration;

    private int pricePence;

}
