package com.github.cb2222124.vlpms.backend.controller.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * Controller advice to provide a more controlled response for validation errors for bad user input.
     *
     * @param ex Exception.
     * @return Response.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleRequestValidationException(Exception ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("message", ex.getMessage());
        return response;
    }

}
