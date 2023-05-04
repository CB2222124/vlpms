package com.github.cb2222124.vlpms.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VES DTO used to hold responses from the DVLA VES service. Additional properties not of interest are not mapped.
 *
 * @param yearOfManufacture The vehicle year of manufacture.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VesResponse(@JsonProperty("yearOfManufacture") int yearOfManufacture) {
}