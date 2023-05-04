package com.github.cb2222124.vlpms.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Registration entity.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@Getter
@Setter
public class Registration {

    /**
     * Vehicle registration is used as the unique identifier.
     */
    @Id
    private String registration;

    private String style;

    /**
     * Each registration may be listed once. JsonBackReference is used to omit this field from serialisation
     * and avoid cyclic dependencies when querying a listing or registration.
     * The orphanRemoval property of the OneToOne relationship ensures that if a registration is removed then any
     * associated listing is removed as well.
     */
    @JsonBackReference
    @OneToOne(mappedBy = "registration", orphanRemoval = true)
    private Listing listing;

    public Registration(String registration, String style) {
        this.registration = registration;
        this.style = style;
    }
}