package com.github.cb2222124.vlpms.backend.service;

import com.github.cb2222124.vlpms.backend.exception.CustomerException;
import com.github.cb2222124.vlpms.backend.exception.ListingException;
import com.github.cb2222124.vlpms.backend.model.Customer;
import com.github.cb2222124.vlpms.backend.model.Listing;
import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.repository.CustomerRepository;
import com.github.cb2222124.vlpms.backend.repository.ListingRepository;
import com.github.cb2222124.vlpms.backend.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    final CustomerRepository customerRepository;
    final RegistrationRepository registrationRepository;
    final ListingRepository listingRepository;

    public CustomerService(CustomerRepository customerRepository, RegistrationRepository registrationRepository, ListingRepository listingRepository) {
        this.customerRepository = customerRepository;
        this.registrationRepository = registrationRepository;
        this.listingRepository = listingRepository;
    }

    @Transactional
    public Customer register(String username) {
        if (customerRepository.findByUsernameIgnoreCase(username).isPresent()) {
            throw new CustomerException(HttpStatus.BAD_REQUEST, "Username already in use");
        }
        Customer customer = new Customer();
        customer.setUsername(username);
        customerRepository.save(customer);
        return customer;
    }

    public Customer login(String username) {
        return customerRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> {
            throw new CustomerException(HttpStatus.NOT_FOUND, "Provided username is not associated with a customer");
        });
    }

    @Transactional
    public Listing addListingToCustomerWishlist(Long id, String registration) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            throw new CustomerException(HttpStatus.NOT_FOUND, "Provided ID is not associated with a customer");
        });
        Listing listing = listingRepository.findById(registration).orElseThrow(() -> {
            throw new ListingException(HttpStatus.NOT_FOUND, "Provided registration is not listed or does not exist");
        });
        if (customer.getWishlistedListings().contains(listing)) {
            throw new ListingException(HttpStatus.BAD_REQUEST, "Provided registration is already wishlisted by the provided customer");
        }
        customer.getWishlistedListings().add(listing);
        customerRepository.save(customer);
        return listing;
    }

    @Transactional
    public List<Listing> removeListingFromCustomerWishlist(Long id, String registration) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            throw new CustomerException(HttpStatus.NOT_FOUND, "Provided ID is not associated with a customer");
        });
        Listing listing = listingRepository.findById(registration).orElseThrow(() -> {
            throw new ListingException(HttpStatus.NOT_FOUND, "Provided registration is not listed or does not exist");
        });
        if (!customer.getWishlistedListings().remove(listing)) {
            throw new ListingException(HttpStatus.BAD_REQUEST, "Provided registration is not wishlisted by the provided customer");
        }
        customerRepository.save(customer);
        return wishlist(id);
    }

    @Transactional
    public List<Listing> wishlist(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            throw new CustomerException(HttpStatus.NOT_FOUND, "Provided ID is not associated with a customer");
        });
        List<Listing> listings = customer.getWishlistedListings();
        Hibernate.initialize(listings);
        return listings;
    }

    @Transactional
    public List<Registration> registrations(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            throw new CustomerException(HttpStatus.NOT_FOUND, "Provided ID is not associated with a customer");
        });
        List<Registration> registrations = customer.getOwnedRegistrations();
        Hibernate.initialize(registrations);
        return registrations;
    }
}
