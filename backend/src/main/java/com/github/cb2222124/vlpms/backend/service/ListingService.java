package com.github.cb2222124.vlpms.backend.service;

import com.github.cb2222124.vlpms.backend.exception.ListingException;
import com.github.cb2222124.vlpms.backend.model.Listing;
import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.repository.RegistrationRepository;
import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ListingService {

    @PersistenceContext
    private EntityManager entityManager;
    final RegistrationRepository registrationRepository;

    public ListingService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    public Listing createAndListRegistration(String registration, int pricePence) {
        if (registrationRepository.findById(registration).isPresent()) {
            throw new ListingException(HttpStatus.BAD_REQUEST, "Registration already exists");
        }

        Registration registrationEntity;
        if (registration.matches(RegistrationRegex.CURRENT)) {
            registrationEntity = new Registration(registration, "Current");
        } else if (registration.matches(RegistrationRegex.PREFIX)) {
            registrationEntity = new Registration(registration, "Prefix");
        } else if (registration.matches(RegistrationRegex.SUFFIX)) {
            registrationEntity = new Registration(registration, "Suffix");
        } else {
            throw new ListingException(HttpStatus.BAD_REQUEST, "Registration does not match any accepted styles");
        }

        Listing listing = new Listing();
        listing.setRegistration(registrationEntity);
        listing.setPricePence(pricePence);

        entityManager.persist(registrationEntity);
        entityManager.persist(listing);

        return listing;
    }

}
