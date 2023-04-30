package com.github.cb2222124.vlpms.backend.service;

import com.github.cb2222124.vlpms.backend.dto.response.VesResponse;
import com.github.cb2222124.vlpms.backend.exception.VesException;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Service
public class VesService {

    //TODO: Provide these fields as env variables or args.
    private final String URI = "https://driver-vehicle-licensing.api.gov.uk/vehicle-enquiry/v1/vehicles";
    private final String KEY = "";

    private final WebClient webClient;

    public VesService() {
        webClient = WebClient.builder()
                .baseUrl(URI)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-api-key", KEY)
                .build();
    }

    public VesResponse queryRegistration(String registration) {
        String request = new JSONObject().appendField("registrationNumber", registration).toJSONString();
        return webClient.post()
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(request.getBytes(StandardCharsets.UTF_8).length))
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new VesException(response.statusCode(), "No data available for target registration"));
                    } else if (response.statusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new VesException(response.statusCode(), "Bad request"));
                    } else if (response.statusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                        return Mono.error(new VesException(response.statusCode(), "Registration lookup service unavailable"));
                    } else {
                        return Mono.error(new VesException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                    }
                })
                .bodyToMono(VesResponse.class)
                .block();
    }
}
