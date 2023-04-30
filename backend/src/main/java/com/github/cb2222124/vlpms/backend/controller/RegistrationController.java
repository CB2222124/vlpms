package com.github.cb2222124.vlpms.backend.controller;

import com.github.cb2222124.vlpms.backend.dto.request.AssignRegistrationRequest;
import com.github.cb2222124.vlpms.backend.dto.request.NewListingRequest;
import com.github.cb2222124.vlpms.backend.dto.response.TransferableResponse;
import com.github.cb2222124.vlpms.backend.dto.response.VesResponse;
import com.github.cb2222124.vlpms.backend.exception.TransferableException;
import com.github.cb2222124.vlpms.backend.exception.VesException;
import com.github.cb2222124.vlpms.backend.model.Listing;
import com.github.cb2222124.vlpms.backend.model.Registration;
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
public class RegistrationController {

    private final TransferableService registrationService;
    private final VesService vesService;

    public RegistrationController(TransferableService registrationService, VesService vesService) {
        this.registrationService = registrationService;
        this.vesService = vesService;
    }

    @PostMapping("/list")
    public Listing list(NewListingRequest listingDTO) {
        return null;
    }

    @PostMapping("/assign")
    public Registration assign(AssignRegistrationRequest registrationDTO) {
        return null;
    }

    @GetMapping("/transferable")
    public ResponseEntity<TransferableResponse> transferable(
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String registration,
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String targetRegistration) {
        VesResponse vesResponse = vesService.queryRegistration(targetRegistration);
        boolean transferable = registrationService.transferable(registration, vesResponse.yearOfManufacture());
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
