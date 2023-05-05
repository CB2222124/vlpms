package com.github.cb2222124.vlpms.backend.controller;

import com.github.cb2222124.vlpms.backend.dto.response.ListingResponse;
import com.github.cb2222124.vlpms.backend.repository.ListingRepository;
import com.github.cb2222124.vlpms.backend.service.ListingService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller used to map listing queries.
 */
@RestController
@RequestMapping("/listing")
@CrossOrigin(origins = "${ALLOWED_ORIGINS}")
public class ListingController {

    private final ListingRepository listingRepository;
    private final ListingService listingService;

    public ListingController(ListingRepository listingRepository, ListingService listingService) {
        this.listingRepository = listingRepository;
        this.listingService = listingService;
    }

    /**
     * Allows listings to be queried by similarity, using the levenshtein Postgres library function. This
     * would be more appropriately achieved using Hibernate Search to avoid using native queries.
     *
     * @param target   The input to match registrations against.
     * @param pageable Pagination argument defining page, size and sort parameters.
     * @return Page of listings sorted by similarity.
     */
    @GetMapping("/search/similar")
    @Cacheable(value = "search", key = "{#target, #pageable}")
    public Page<ListingResponse> similar(String target, Pageable pageable) {
        return listingService.mapListingPageResponse(listingRepository.findBySimilarity(target, pageable), pageable);
    }

    /**
     * Allows listings to be queried for a specific registration style.
     *
     * @param style    The registration style to match.
     * @param pageable Pagination argument defining page, size and sort parameters.
     * @return Page of listings.
     */
    @GetMapping("/search/style")
    @Cacheable(value = "search", key = "{#style, #pageable}")
    public Page<ListingResponse> style(String style, Pageable pageable) {
        return listingService.mapListingPageResponse(listingRepository.findByRegistrationStyle(style, pageable), pageable);
    }

    /**
     * Allows listings to be queried.
     *
     * @param pageable Pagination argument defining page, size and sort parameters.
     * @return Page of listings.
     */
    @GetMapping("/search")
    @Cacheable(value = "search", key = "{#pageable}")
    public Page<ListingResponse> search(Pageable pageable) {
        return listingService.mapListingPageResponse(listingRepository.findAll(pageable), pageable);
    }
}
