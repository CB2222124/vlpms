package com.github.cb2222124.vlpms.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private String username;

    //private String password;

    //private String paymentGatewayToken;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Registration> ownedRegistrations;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Listing> wishlistedListings;
}
