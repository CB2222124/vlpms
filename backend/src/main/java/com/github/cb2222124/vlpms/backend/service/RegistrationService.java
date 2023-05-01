package com.github.cb2222124.vlpms.backend.service;

import com.github.cb2222124.vlpms.backend.exception.CustomerException;
import com.github.cb2222124.vlpms.backend.exception.ListingException;
import com.github.cb2222124.vlpms.backend.model.Customer;
import com.github.cb2222124.vlpms.backend.model.Listing;
import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.repository.CustomerRepository;
import com.github.cb2222124.vlpms.backend.repository.ListingRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final CustomerRepository customerRepository;
    private final ListingRepository listingRepository;

    public RegistrationService(CustomerRepository customerRepository, ListingRepository listingRepository) {
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


}
