package com.github.cb2222124.vlpms.backend.repository;

import com.github.cb2222124.vlpms.backend.model.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@SuppressWarnings("unused")
@RepositoryRestResource(collectionResourceRel = "listing", path = "listing")
public interface ListingRepository extends Repository<Listing, String> {

    @Query(value = "SELECT * FROM listing ORDER BY levenshtein(:target, registration_registration)", nativeQuery = true)
    Page<Listing> similar(@Param("target") String target, Pageable pageable);
}