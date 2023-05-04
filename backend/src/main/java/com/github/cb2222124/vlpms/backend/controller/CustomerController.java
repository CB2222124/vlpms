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

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:9001")
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Validated CustomerLoginRequest request) {
        Customer customer = customerService.register(request.username());
        return ResponseEntity.ok(customer.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody @Validated CustomerLoginRequest request) {
        Customer customer = customerService.login(request.username());
        return ResponseEntity.ok(customer.getId());
    }

    @PostMapping("/wishlist")
    public ResponseEntity<Listing> addWishlist(@RequestBody @Validated CustomerWishlistRequest request) {
        return ResponseEntity.ok(customerService.addListingToCustomerWishlist(request.customerId(), request.registration()));
    }

    @DeleteMapping("/wishlist")
    public ResponseEntity<List<Listing>> removeWishlist(
            @RequestParam Long id,
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String registration) {
        return ResponseEntity.ok(customerService.removeListingFromCustomerWishlist(id, registration));
    }

    @GetMapping("{id}/wishlist")
    public ResponseEntity<List<Listing>> wishlist(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.wishlist(id));
    }

    @GetMapping("{id}/registrations")
    public ResponseEntity<List<Registration>> registrations(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.registrations(id));
    }

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<CustomerException> handleCustomerException(CustomerException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex);
    }

    @ExceptionHandler(ListingException.class)
    public ResponseEntity<ListingException> handleListingException(ListingException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex);
    }

}
