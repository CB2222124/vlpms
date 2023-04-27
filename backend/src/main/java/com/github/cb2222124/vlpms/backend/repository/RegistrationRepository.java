package com.github.cb2222124.vlpms.backend.repository;

import com.github.cb2222124.vlpms.backend.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@SuppressWarnings("unused")
@RepositoryRestResource(collectionResourceRel = "registration", path = "registration")
public interface RegistrationRepository extends Repository<Registration, String> {
    Optional<Registration> findByRegistration(String id);

    Iterable<Registration> findAll();

    Page<Registration> findAll(Pageable pageable);

    Iterable<Registration> findAll(Sort sort);

    @RestResource(exported = false)
    void save(Registration entity);
}