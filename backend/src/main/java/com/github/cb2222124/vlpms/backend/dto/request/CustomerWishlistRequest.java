package com.github.cb2222124.vlpms.backend.dto.request;

import com.github.cb2222124.vlpms.backend.util.RegistrationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerWishlistRequest(
        Long customerId,
        @NotBlank @Pattern(regexp = RegistrationRegex.ALL) String registration) {
}
