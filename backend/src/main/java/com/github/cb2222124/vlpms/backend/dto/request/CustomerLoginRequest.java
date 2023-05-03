package com.github.cb2222124.vlpms.backend.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CustomerLoginRequest(@NotEmpty String username) {
}
