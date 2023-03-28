package com.assignment.openweatherservice.model.weatherstack;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Error {
    private String code;
    private String type;
    private String info;
}
