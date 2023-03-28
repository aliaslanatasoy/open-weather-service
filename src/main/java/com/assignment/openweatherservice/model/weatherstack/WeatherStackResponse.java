package com.assignment.openweatherservice.model.weatherstack;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class WeatherStackResponse implements Serializable {
    private Request request;
    private Location location;
    private Current current;
    private String success;
    private Error error;
}
