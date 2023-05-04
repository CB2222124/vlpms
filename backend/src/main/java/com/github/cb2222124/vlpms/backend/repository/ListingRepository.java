package com.github.cb2222124.vlpms.backend.repository;

import com.github.cb2222124.vlpms.backend.model.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.Optional;

/**
 * Listing repository used to query listings.
 */
@RepositoryRestResource(collectionResourceRel = "listing", path = "listing")
@CrossOrigin(origins = "http://localhost:9001")
public interface ListingRepository extends ListPagingAndSortingRepository<Listing, String> {

    /**
     * Allows listings to be queried by similarity, using the levenshtein Postgres library function. This
     * would be more appropriately achieved using Hibernate Search to avoid using native queries.
     *
     * @param target The input to match registrations against.
     * @return Page of listings sorted by similarity.
     */
    @SuppressWarnings({"unused", "SqlSignature"})
    @Query(value = "SELECT * FROM listing ORDER BY levenshtein(:target, registration)", nativeQuery = true)
    Page<Listing> findBySimilarity(@Param("target") String target, Pageable pageable);

    /**
     * Allows listed registrations to be queried by a specific set of styles.
     *
     * @param styles The styles that should be allowed.
     * @return Page of listings.
     */
    @SuppressWarnings("unused")
    Page<Listing> findByRegistrationStyleIn(Collection<String> styles, Pageable pageable);

    @RestResource(exported = false)
    Optional<Listing> findById(String registration);

    @RestResource(exported = false)
    void delete(Listing listing);
}