package com.github.cb2222124.vlpms.backend.controller;

import com.github.cb2222124.vlpms.backend.dto.VesResponse;
import com.github.cb2222124.vlpms.backend.service.VesService;
import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class RegistrationController {

    private final VesService vesService;

    public RegistrationController(VesService vesService) {
        this.vesService = vesService;
    }

    @GetMapping("/transferable")
    public boolean transferable(
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String registration,
            @RequestParam @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String targetRegistration) {
        VesResponse response = vesService.queryRegistration(targetRegistration);
        return registrationYoungerThanDate(registration, response.yearOfManufacture());
    }

    private boolean registrationYoungerThanDate(String registration, String date) {
        //TODO: Calculate if desired registration describes a date younger than the target vehicle.
        return true;
    }
}
