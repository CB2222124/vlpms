package com.github.cb2222124.vlpms.backend.repository;

import com.github.cb2222124.vlpms.backend.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Registration repository used for internal operations. Not mapped.
 */
public interface RegistrationRepository extends JpaRepository<Registration, String> {

}
