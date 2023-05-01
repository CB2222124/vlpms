package com.github.cb2222124.vlpms.backend.repository;

import com.github.cb2222124.vlpms.backend.model.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "listing", path = "listing")
@CrossOrigin(origins = "http://localhost:9001")
public interface ListingRepository extends Repository<Listing, String> {

    @SuppressWarnings("unused")
    @Query(value = "SELECT * FROM listing ORDER BY levenshtein(:target, registration)", nativeQuery = true)
    Page<Listing> similar(@Param("target") String target, Pageable pageable);

    @RestResource(exported = false)
    Optional<Listing> findById(String registration);

    @RestResource(exported = false)
    void delete(Listing listing);
}