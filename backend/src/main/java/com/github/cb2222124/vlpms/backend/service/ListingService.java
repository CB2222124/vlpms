package com.github.cb2222124.vlpms.backend.service;

import com.github.cb2222124.vlpms.backend.util.SerializablePage;
import com.github.cb2222124.vlpms.backend.dto.response.ListingResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final CacheService cacheService;

    public ListingService(RegistrationRepository registrationRepository, CustomerRepository customerRepository, ListingRepository listingRepository, CacheService cacheService) {
        this.registrationRepository = registrationRepository;
        this.customerRepository = customerRepository;
        this.listingRepository = listingRepository;
        this.cacheService = cacheService;
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
        cacheService.clearCache("search");
        return listing.getRegistration();
    }


    /**
     * Converts a Page of listings to a Page of listing DTOs that can be serialised.
     *
     * @param listings The page of listings.
     * @param pageable Pagination information.
     * @return A serializable page of listing response DTOs.
     */
    public SerializablePage<ListingResponse> mapListingPageResponse(Page<Listing> listings, Pageable pageable) {
        List<ListingResponse> listingResponses = listings.stream().map(Listing::convertToListingResponse).toList();
        return new SerializablePage<>(new PageImpl<>(listingResponses, pageable, listings.getTotalElements()));
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
