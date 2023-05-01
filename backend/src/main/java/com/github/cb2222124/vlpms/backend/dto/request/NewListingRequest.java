package com.github.cb2222124.vlpms.backend.dto.request;

import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

@Validated
public record NewListingRequest(
        @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String registration,
        int pricePence) {
}