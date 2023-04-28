package com.github.cb2222124.vlpms.backend.dto;

import com.github.cb2222124.vlpms.backend.model.Customer;
import com.github.cb2222124.vlpms.backend.model.Registration;

public record AssignRegistrationDTO(Customer customer, Registration registration) {
}
