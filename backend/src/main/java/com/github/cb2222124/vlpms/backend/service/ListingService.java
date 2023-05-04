package com.github.cb2222124.vlpms.backend.service;

import com.github.cb2222124.vlpms.backend.exception.CustomerException;
import com.github.cb2222124.vlpms.backend.exception.ListingException;
import com.github.cb2222124.vlpms.backend.model.Customer;
import com.github.cb2222124.vlpms.backend.model.Listing;
import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.repository.CustomerRepository;
import com.github.cb2222124.vlpms.backend.repository.ListingRepository;
import com.github.cb2222124.vlpms.backend.repository.RegistrationRepository;
import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Listing service used to handle listing related business logic.
 */
@Service
public class ListingService {

    @PersistenceContext
    private EntityManager entityManager;
    private final RegistrationRepository registrationRepository;
    private final CustomerRepository customerRepository;
    private final ListingRepository listingRepository;

    public ListingService(RegistrationRepository registrationRepository, CustomerRepository customerRepository, ListingRepository listingRepository) {
        this.registrationRepository = registrationRepository;
        this.customerRepository = customerRepository;
        this.listingRepository = listingRepository;
    }

    @Transactional
    public Registration transferListingToCustomer(Long id, String registration) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            throw new CustomerException(HttpStatus.NOT_FOUND, "Provided ID is not associated with a customer");
        });
        Listing listing = listingRepository.findById(registration).orElseThrow(() -> {
            throw new ListingException(HttpStatus.NOT_FOUND, "Provided registration is not listed or does not exist");
        });
        listingRepository.delete(listing);
        customer.getOwnedRegistrations().add(listing.getRegistration());
        customerRepository.save(customer);
        return listing.getRegistration();
    }

    /**
     * Unused operation designed to create and list a new registration. This would be useful for an administrative
     * role in a future iteration.
     *
     * @param registration The new registration.
     * @param pricePence   The listing price in pence.
     * @return The new listing.
     */
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
