package com.github.cb2222124.vlpms.backend.repository;

import com.github.cb2222124.vlpms.backend.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@SuppressWarnings("unused")
@RepositoryRestResource(collectionResourceRel = "registration", path = "registration")
public interface RegistrationRepository extends Repository<Registration, String> {
    Iterable<Registration> findAll();

    Page<Registration> findAll(Pageable pageable);

    Iterable<Registration> findAll(Sort sort);

    Optional<Registration> registration(String id);

    @Query(value = "SELECT * FROM registration ORDER BY levenshtein(:target, registration) LIMIT(:size) OFFSET(:size * :page)", nativeQuery = true)
    Iterable<Registration> matches(@Param("target") String target, @Param("page") int page, @Param("size") int size);

    @RestResource(exported = false)
    void save(Registration entity);
}