package com.github.cb2222124.vlpms.backend.dto.request;

import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @param customerId Customer ID.
 * @param registration Registration (Present and matches registration regex).
 */
public record CustomerRegistrationRequest(
        Long customerId,
        @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String registration) {
}
