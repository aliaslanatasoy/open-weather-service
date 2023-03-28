package com.assignment.openweatherservice.controller;

import com.assignment.openweatherservice.model.GetWeatherResponse;
import com.assignment.openweatherservice.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/getWeather/{city}")
    public ResponseEntity<GetWeatherResponse> getWeather(@PathVariable("city") String city) {
        return ok().body(weatherService.getWeather(city));
    }
}
