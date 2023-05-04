package com.github.cb2222124.vlpms.backend.controller;

import com.github.cb2222124.vlpms.backend.dto.request.CustomerRegistrationRequest;
import com.github.cb2222124.vlpms.backend.dto.response.TransferableResponse;
import com.github.cb2222124.vlpms.backend.dto.response.VesResponse;
import com.github.cb2222124.vlpms.backend.exception.ListingException;
import com.github.cb2222124.vlpms.backend.exception.TransferableException;
import com.github.cb2222124.vlpms.backend.exception.VesException;
import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.service.ListingService;
import com.github.cb2222124.vlpms.backend.service.TransferableService;
import com.github.cb2222124.vlpms.backend.service.VesService;
import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller used to map registration operations.
 */
@RestController
@RequestMapping("/registration")
@CrossOrigin(origins = "http://localhost:9001")
public class RegistrationController {

    private final ListingService listingService;
    private final TransferableService transferableService;
    private final VesService vesService;

    public RegistrationController(ListingService listingService, TransferableService transferableService, VesService vesService) {
        this.listingService = listingService;
        this.transferableService = transferableService;
        this.vesService = vesService;
    }

    /**
     * Assigns a listed registration to a customer. This operation removes it's associated listing and subsequently
     * from every customer's wishlist.
     *
     * @param request Request body containing listing and target customer.
     * @return The modified registration.
     */
    @PostMapping("/assignListing")
    public ResponseEntity<Registration> addRegistration(@RequestBody @Validated CustomerRegistrationRequest request) {
        return ResponseEntity.ok(listingService.transferListingToCustomer(request.customerId(), request.registration()));
    }

    /**
     * Checks if a registration held in the database is able to be assigned to a real-world vehicle,
     * queried by current registration using a DVLA service.
     *
     * @param registration       The registration.
     * @param targetRegistration The target vehicle registration.
     * @return Ok response containing True if it is transferable, False otherwise.
     */
    @GetMapping("/transferable")
    public ResponseEntity<TransferableResponse> transferable(
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String registration,
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String targetRegistration) {
        VesResponse vesResponse = vesService.queryRegistration(targetRegistration);
        boolean transferable = transferableService.transferable(registration, vesResponse.yearOfManufacture());
        if (transferable) return ResponseEntity.ok(new TransferableResponse(true));
        return ResponseEntity.ok(new TransferableResponse(false,
                "Registrations cannot indicate that a vehicle is younger than its year of manufacture"));
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

    /**
     * Handles VES exceptions.
     *
     * @param ex VES related exception.
     * @return Response entity wrapping the exception.
     */
    @ExceptionHandler(VesException.class)
    public ResponseEntity<TransferableResponse> handleVesException(VesException ex) {
        TransferableResponse transferableResponse = new TransferableResponse(ex.getStatusCode().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(transferableResponse);
    }

    /**
     * Handles transferable exceptions.
     *
     * @param ex Transferable related exception.
     * @return Response entity wrapping the exception.
     */
    @ExceptionHandler(TransferableException.class)
    public ResponseEntity<TransferableResponse> handleTransferableException(TransferableException ex) {
        TransferableResponse transferableResponse = new TransferableResponse(ex.getStatusCode().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(transferableResponse);
    }
}
