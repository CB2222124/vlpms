package com.github.cb2222124.vlpms.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * Response DTO for transferable queries containing status, transferable status and associated message.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class TransferableResponse {

    private Boolean transferable;
    private Integer statusCode;
    private String message;

    public TransferableResponse(boolean transferable) {
        this.transferable = transferable;
    }

    public TransferableResponse(boolean transferable, String message) {
        this.transferable = transferable;
        this.message = message;
    }

    public TransferableResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
