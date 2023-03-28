package com.assignment.openweatherservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@RequiredArgsConstructor
@Setter
@Getter
public class GetWeatherResponse implements Serializable {
    private String temperature;
}
