package com.github.cb2222124.vlpms.backend.repository;

import com.github.cb2222124.vlpms.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsernameIgnoreCase(String username);
}