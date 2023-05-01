package com.github.cb2222124.vlpms.backend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

@Validated
public record CustomerLoginRequest(@NotEmpty String username) {
}
