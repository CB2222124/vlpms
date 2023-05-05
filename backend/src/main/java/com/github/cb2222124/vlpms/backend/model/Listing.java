package com.github.cb2222124.vlpms.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.cb2222124.vlpms.backend.dto.response.ListingResponse;
import com.github.cb2222124.vlpms.backend.dto.response.RegistrationResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Listing entity.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@Getter
@Setter
public class Listing {

    /**
     * Uses the registration entities ID (Vehicle registration) as its ID.
     * JsonBackReference omits this from serialisation.
     */
    @JsonBackReference
    @Id
    private String id;

    private int pricePence;

    private LocalDate dateListed;

    /**
     * Maps parent registration entities ID (Vehicle registration) as its own ID.
     * JsonManagedReference includes this entity in serialisation.
     */
    @JsonManagedReference
    @MapsId
    @JoinColumn(name = "registration")
    @OneToOne(fetch = FetchType.LAZY)
    private Registration registration;

    /**
     * Many listings can be wishlisted by many customers. JsonBackReference omits this from serialisation
     * to prevent cyclic dependencies when querying a listing.
     */
    @JsonBackReference
    @ManyToMany(mappedBy = "wishlistedListings")
    private List<Customer> customers;

    /**
     * PreRemove is used to remove the relationship (Customer wishlist) between a listing and all relevant
     * customers before removing this entity.
     */
    @PreRemove
    private void removeWishlistReferences() {
        for (Customer customer : customers) {
            customer.getWishlistedListings().remove(this);
        }
    }

    /**
     * Converts a listing entity to a listing response.
     *
     * @param listing The listing to convert.
     * @return A response DTO representing a listing.
     */
    public static ListingResponse convertToListingResponse(Listing listing) {
        Registration registration = listing.getRegistration();
        return new ListingResponse(listing.getPricePence(), listing.getDateListed(),
                new RegistrationResponse(registration.getRegistration(), registration.getStyle()));
    }
}
