package com.assignment.openweatherservice.service;

import com.assignment.openweatherservice.exception.ApiWeatherServiceException;
import com.assignment.openweatherservice.exception.WeatherStackServiceNotAvailableException;
import com.assignment.openweatherservice.mapper.GetWeatherResponseMapper;
import com.assignment.openweatherservice.mapper.WeatherEntityMapper;
import com.assignment.openweatherservice.model.GetWeatherResponse;
import com.assignment.openweatherservice.model.weatherstack.Error;
import com.assignment.openweatherservice.model.weatherstack.WeatherStackResponse;
import com.assignment.openweatherservice.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final WeatherStackService weatherStackService;
    private final GetWeatherResponseMapper getWeatherResponseMapper;
    private final WeatherEntityMapper weatherEntityMapper;

    @Cacheable(value = "weatherCache")
    public GetWeatherResponse getWeather(String city) {
        log.info("Starting to call service to fetch temperature for city: {}", city);
        WeatherStackResponse weatherStackResponse = weatherStackService.getWeather(city);

        String temperature = getTemperature(city, weatherStackResponse);

        return getWeatherResponseMapper.map(temperature);
    }

    private String getTemperature(String city, WeatherStackResponse response) {
        if (!isNull(response) && !isNull(response.getCurrent())) {
            String temperature = response.getCurrent().getTemperature();
            saveWeatherData(temperature, city);
            return temperature;
        }

        if (!isNull(response) && !isNull(response.getError())) {
            Error error = response.getError();
            log.error("WeatherStack returned an error response with code: {} and message: {}", error.getCode(), error.getInfo());
            throw new ApiWeatherServiceException(error.getInfo(), error.getCode());
        }
        throw new WeatherStackServiceNotAvailableException("Given city not found in open weather.");
    }

    private void saveWeatherData(String temperature, String city) {
        weatherRepository.save(weatherEntityMapper.map(city, temperature));
    }
}
