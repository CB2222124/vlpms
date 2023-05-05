package com.github.cb2222124.vlpms.backend.repository;

import com.github.cb2222124.vlpms.backend.model.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Listing repository used to query listings, not mapped.
 */
public interface ListingRepository extends JpaRepository<Listing, String> {

    /**
     * Allows listings to be queried by similarity, using the levenshtein Postgres library function. This
     * would be more appropriately achieved using Hibernate Search to avoid using native queries.
     *
     * @param target The input to match registrations against.
     * @return Page of listings sorted by similarity.
     */
    @Query(value = "SELECT * FROM listing ORDER BY levenshtein(:target, registration)", nativeQuery = true)
    Page<Listing> findBySimilarity(@Param("target") String target, Pageable pageable);

    /**
     * Allows listed registrations to be queried by a specific style.
     *
     * @param style The style that should be allowed.
     * @return Page of listings.
     */
    Page<Listing> findByRegistrationStyle(String style, Pageable pageable);
}