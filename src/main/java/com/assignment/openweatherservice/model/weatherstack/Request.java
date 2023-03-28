package com.assignment.openweatherservice.model.weatherstack;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Request implements Serializable {
    private String type;
    private String query;
    private String language;
    private String unit;
}
