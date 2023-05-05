package com.github.cb2222124.vlpms.backend.controller;

import com.github.cb2222124.vlpms.backend.dto.request.CustomerLoginRequest;
import com.github.cb2222124.vlpms.backend.dto.request.CustomerWishlistRequest;
import com.github.cb2222124.vlpms.backend.exception.CustomerException;
import com.github.cb2222124.vlpms.backend.exception.ListingException;
import com.github.cb2222124.vlpms.backend.model.Customer;
import com.github.cb2222124.vlpms.backend.model.Listing;
import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.service.CustomerService;
import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller used to map customer operations.
 */
@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "${ALLOWED_ORIGINS}")
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Registers a new customer provided they do not already exist and returns their ID.
     *
     * @param request The customer registration request body.
     * @return Ok response with the generated customer ID.
     */
    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Validated CustomerLoginRequest request) {
        Customer customer = customerService.register(request.username());
        return ResponseEntity.ok(customer.getId());
    }

    /**
     * Takes a customer login request and returns their ID if it is valid.
     *
     * @param request The customer login request body.
     * @return Ok response with the customer ID.
     */
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody @Validated CustomerLoginRequest request) {
        Customer customer = customerService.login(request.username());
        return ResponseEntity.ok(customer.getId());
    }

    /**
     * Adds a listing to the customers wishlist.
     *
     * @param request Add to wishlist request body.
     * @return Ok response with the added listing.
     */
    @PostMapping("/wishlist")
    public ResponseEntity<Listing> addWishlist(@RequestBody @Validated CustomerWishlistRequest request) {
        return ResponseEntity.ok(customerService.addListingToCustomerWishlist(request.customerId(), request.registration()));
    }

    /**
     * Removes a listing from the customers wishlist.
     *
     * @param id           The customer ID.
     * @param registration The listing's registration.
     * @return The updated wishlist.
     */
    @DeleteMapping("/wishlist")
    public ResponseEntity<List<Listing>> removeWishlist(
            @RequestParam Long id,
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String registration) {
        return ResponseEntity.ok(customerService.removeListingFromCustomerWishlist(id, registration));
    }

    /**
     * Retrieves a customers wishlist.
     *
     * @param id Customer ID.
     * @return Wishlist as a list of listings.
     */
    @GetMapping("{id}/wishlist")
    public ResponseEntity<List<Listing>> wishlist(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.wishlist(id));
    }

    /**
     * Retrieves the registrations owned by a customer.
     *
     * @param id Customer ID.
     * @return List of registrations owned.
     */
    @GetMapping("{id}/registrations")
    public ResponseEntity<List<Registration>> registrations(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.registrations(id));
    }

    /**
     * Handles customer exceptions.
     *
     * @param ex Customer related exception.
     * @return Response entity wrapping the exception.
     */
    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<CustomerException> handleCustomerException(CustomerException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex);
    }

    /**
     * Handles listing exceptions.
     *
     * @param ex Listing related exception.
     * @return Response entity wrapping the exception.
     */
    @ExceptionHandler(ListingException.class)
    public ResponseEntity<ListingException> handleListingException(ListingException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex);
    }

}
