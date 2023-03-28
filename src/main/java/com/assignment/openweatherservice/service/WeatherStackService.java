package com.assignment.openweatherservice.service;

import com.assignment.openweatherservice.client.WeatherStackFeignClient;
import com.assignment.openweatherservice.exception.WeatherStackServiceNotAvailableException;
import com.assignment.openweatherservice.model.weatherstack.WeatherStackResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherStackService {
    private final WeatherStackFeignClient weatherStackFeignClient;
    @Value("${openweather.accessKey}")
    private String accessKey;

    public WeatherStackResponse getWeather(String city) {
        try {
            return weatherStackFeignClient.getWeather(accessKey, city);
        } catch (Exception e) {
            log.info("Open Weather is not reachable: {}", e.getMessage());
            throw new WeatherStackServiceNotAvailableException("Open weather not available.");
        }
    }
}
