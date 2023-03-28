package com.assignment.openweatherservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeatherStackServiceNotAvailableException extends RuntimeException {
    private final String message;
}
