package com.assignment.openweatherservice.client;

import com.assignment.openweatherservice.model.weatherstack.WeatherStackResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@FeignClient(value = "openWeather", url = "${openweather.baseUrl}")
public interface WeatherStackFeignClient {
    @Value("${openweather.accessKey}")
    @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    WeatherStackResponse getWeather(@RequestParam(value = "access_key") String accessKey,
                                    @RequestParam(value = "query") String city);
}
