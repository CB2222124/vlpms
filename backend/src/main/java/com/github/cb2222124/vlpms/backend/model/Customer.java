package com.github.cb2222124.vlpms.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_owned_registrations",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "registration"))
    private List<Registration> ownedRegistrations;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_wishlisted_listings",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "registration"))
    private List<Listing> wishlistedListings;
}
