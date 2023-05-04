package com.github.cb2222124.vlpms.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * @param username Customer username (Present and not empty).
 */
public record CustomerLoginRequest(@NotBlank String username) {
}