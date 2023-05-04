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

/**
 * Customer service used to handle customer related business logic.
 */
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

    /**
     * Registers a customer with the provided username, given it is not already in use. In a deployed application
     * there would be more registration parameters.
     *
     * @param username The desired username.
     * @return The new customers ID.
     */
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

    /**
     * Provides the ID of a customer associated with the provided username, given it exists. In a deployed application
     * this would instead be a session ID or authentication token (JWT etc).
     *
     * @param username Username.
     * @return The customers ID.
     */
    public Customer login(String username) {
        return customerRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> {
            throw new CustomerException(HttpStatus.NOT_FOUND, "Provided username is not associated with a customer");
        });
    }

    /**
     * Adds the provided listing to a customers wishlist, provided the listing exists and is not already wishlisted.
     *
     * @param id           Customer ID.
     * @param registration Listing registration.
     * @return The listing added.
     */
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

    /**
     * Removes the provided listing from a customers wishlist, provided the listing exists and is wishlisted.
     *
     * @param id           Customer ID.
     * @param registration Listing registration.
     * @return The modified wishlist.
     */
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

    /**
     * Gets the wishlist for a given customer.
     *
     * @param id Customer ID.
     * @return Wishlist.
     */
    @Transactional
    public List<Listing> wishlist(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            throw new CustomerException(HttpStatus.NOT_FOUND, "Provided ID is not associated with a customer");
        });
        List<Listing> listings = customer.getWishlistedListings();
        Hibernate.initialize(listings);
        return listings;
    }

    /**
     * Gets the owned registrations for a given customer.
     *
     * @param id Customer ID.
     * @return List of registrations.
     */
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
