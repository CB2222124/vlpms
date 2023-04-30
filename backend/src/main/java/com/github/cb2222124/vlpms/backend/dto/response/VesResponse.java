package com.github.cb2222124.vlpms.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VesResponse(@JsonProperty("yearOfManufacture") int yearOfManufacture) {
}