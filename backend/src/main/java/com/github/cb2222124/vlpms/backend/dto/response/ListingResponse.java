package com.github.cb2222124.vlpms.backend.dto.response;

import java.time.LocalDate;

/**
 * Response DTO for listing.
 *
 * @param pricePence   Price in pence.
 * @param dateListed   Date listed.
 * @param registration Registration DTO.
 */
public record ListingResponse(int pricePence, LocalDate dateListed, RegistrationResponse registration) {
}
