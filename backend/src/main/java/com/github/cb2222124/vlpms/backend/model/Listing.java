package com.github.cb2222124.vlpms.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@Getter
@Setter
public class Listing {

    @JsonBackReference
    @Id
    private String id;

    @JsonManagedReference
    @MapsId
    @JoinColumn(name = "registration")
    @OneToOne(fetch = FetchType.LAZY)
    private Registration registration;

    private int pricePence;

    private LocalDate dateListed;

}
