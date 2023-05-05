package com.github.cb2222124.vlpms.backend.service;

import com.github.cb2222124.vlpms.backend.dto.response.VesResponse;
import com.github.cb2222124.vlpms.backend.exception.VesException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * VES service used to query the DVLA VES service.
 */
@Service
public class VesService {

    @Value("${VES_KEY}")
    private String KEY;
    @Value("${VES_URI}")
    private String URI;

    private final WebClient webClient;

    public VesService() {
        webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Queries the VES service using WebClient and maps the response to a DTO representing the useful data.
     *
     * @param registration Registration to query.
     * @return DTO containing useful information.
     */
    public VesResponse queryRegistration(String registration) {
        String request = new JSONObject().appendField("registrationNumber", registration).toJSONString();
        return webClient.post()
                .uri(URI)
                .header("x-api-key", KEY)
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
