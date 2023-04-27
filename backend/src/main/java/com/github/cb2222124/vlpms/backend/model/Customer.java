package com.github.cb2222124.vlpms.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    private String username;

    private String password;

    private String paymentGatewayToken;

    @OneToMany
    private List<Registration> ownedRegistrations;
}
