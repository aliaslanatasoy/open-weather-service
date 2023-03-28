package com.assignment.openweatherservice.controller.advice;

import com.assignment.openweatherservice.exception.ApiWeatherServiceException;
import com.assignment.openweatherservice.exception.WeatherStackServiceNotAvailableException;
import com.assignment.openweatherservice.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class WeatherControllerAdvice {

    @ExceptionHandler(ApiWeatherServiceException.class)
    public ResponseEntity<ApiError> handleApiWeatherServiceException(ApiWeatherServiceException apiWeatherServiceException) {
        log.error("Error occurred while fetching info from external service: {}", apiWeatherServiceException.getMessage());

        ApiError apiError = new ApiError();
        apiError.setMessage(apiWeatherServiceException.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

    @ExceptionHandler(WeatherStackServiceNotAvailableException.class)
    public ResponseEntity<ApiError> handleOpenWeatherServiceNotAvailableException(WeatherStackServiceNotAvailableException weatherStackServiceNotAvailableException) {
        log.error("WeatherStack service is not reachable at the moment");

        ApiError apiError = new ApiError();
        apiError.setMessage("We are facing internal issues, please try again later.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }
}
