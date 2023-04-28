package com.github.cb2222124.vlpms.backend.controller;

import com.github.cb2222124.vlpms.backend.dto.AssignRegistrationDTO;
import com.github.cb2222124.vlpms.backend.dto.NewListingDTO;
import com.github.cb2222124.vlpms.backend.dto.VesResponseDTO;
import com.github.cb2222124.vlpms.backend.model.Listing;
import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.service.VesService;
import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/registration")
public class RegistrationController {

    private final VesService vesService;

    public RegistrationController(VesService vesService) {
        this.vesService = vesService;
    }

    @GetMapping("/transferable")
    public boolean transferable(
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String registration,
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String targetRegistration) {
        VesResponseDTO response = vesService.queryRegistration(targetRegistration);
        return vesService.registrationYoungerThanDate(registration, response.yearOfManufacture());
    }

    @PostMapping("/list")
    public Listing list(NewListingDTO listingDTO) {
        return null;
    }

    @PostMapping("/assign")
    public Registration assign(AssignRegistrationDTO registrationDTO) {
        return null;
    }
}
