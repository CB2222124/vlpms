package com.github.cb2222124.vlpms.backend.controller;

import com.github.cb2222124.vlpms.backend.dto.request.CustomerRegistrationRequest;
import com.github.cb2222124.vlpms.backend.dto.request.NewListingRequest;
import com.github.cb2222124.vlpms.backend.dto.response.TransferableResponse;
import com.github.cb2222124.vlpms.backend.dto.response.VesResponse;
import com.github.cb2222124.vlpms.backend.exception.ListingException;
import com.github.cb2222124.vlpms.backend.exception.TransferableException;
import com.github.cb2222124.vlpms.backend.exception.VesException;
import com.github.cb2222124.vlpms.backend.model.Listing;
import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.service.ListingService;
import com.github.cb2222124.vlpms.backend.service.RegistrationService;
import com.github.cb2222124.vlpms.backend.service.TransferableService;
import com.github.cb2222124.vlpms.backend.service.VesService;
import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/registration")
@CrossOrigin(origins = "http://localhost:9001")
public class RegistrationController {

    private final ListingService listingService;
    private final RegistrationService registrationService;
    private final TransferableService transferableService;
    private final VesService vesService;

    public RegistrationController(ListingService listingService, RegistrationService registrationService, TransferableService transferableService, VesService vesService) {
        this.listingService = listingService;
        this.registrationService = registrationService;
        this.transferableService = transferableService;
        this.vesService = vesService;
    }

    @PostMapping("/createAndList")
    public ResponseEntity<Listing> list(@RequestBody @Validated NewListingRequest listingDTO) {
        return ResponseEntity.ok(listingService.createAndListRegistration(listingDTO.registration(), listingDTO.pricePence()));
    }

    @PostMapping("/assignListing")
    public ResponseEntity<Registration> addRegistration(@RequestBody @Validated CustomerRegistrationRequest request) {
        return ResponseEntity.ok(registrationService.transferListingToCustomer(request.customerId(), request.registration()));
    }

    @ExceptionHandler(ListingException.class)
    public ResponseEntity<ListingException> handleListingException(ListingException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex);
    }

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

    @ExceptionHandler(VesException.class)
    public ResponseEntity<TransferableResponse> handleVesException(VesException ex) {
        TransferableResponse transferableResponse = new TransferableResponse(ex.getStatusCode().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(transferableResponse);
    }

    @ExceptionHandler(TransferableException.class)
    public ResponseEntity<TransferableResponse> handleTransferableException(TransferableException ex) {
        TransferableResponse transferableResponse = new TransferableResponse(ex.getStatusCode().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(transferableResponse);
    }
}
