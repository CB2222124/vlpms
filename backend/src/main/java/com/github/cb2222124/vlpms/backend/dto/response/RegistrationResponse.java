package com.github.cb2222124.vlpms.backend.dto.response;

/**
 * Response DTO for registration.
 *
 * @param registration Vehicle registration.
 * @param style        Registration style.
 */
public record RegistrationResponse(String registration, String style) {
}