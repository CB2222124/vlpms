package com.github.cb2222124.vlpms.backend.repository;

import com.github.cb2222124.vlpms.backend.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "registration", path = "registration")
public interface RegistrationRepository extends JpaRepository<Registration, String> {
}
