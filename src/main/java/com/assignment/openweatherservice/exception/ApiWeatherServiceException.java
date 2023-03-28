package com.assignment.openweatherservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiWeatherServiceException extends RuntimeException {
    private final String message;
    private final String code;
}
