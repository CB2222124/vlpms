package com.github.cb2222124.vlpms.backend.service;

import com.github.cb2222124.vlpms.backend.dto.VesResponse;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
                .bodyToMono(VesResponse.class)
                .block();
    }
}
