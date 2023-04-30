package com.github.cb2222124.vlpms.backend.dto.request;

import com.github.cb2222124.vlpms.backend.model.Customer;
import com.github.cb2222124.vlpms.backend.model.Registration;

public record AssignRegistrationRequest(Customer customer, Registration registration) {
}
