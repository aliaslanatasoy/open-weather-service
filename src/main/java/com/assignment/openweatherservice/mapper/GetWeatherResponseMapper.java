package com.assignment.openweatherservice.mapper;

import com.assignment.openweatherservice.model.GetWeatherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GetWeatherResponseMapper {
    @Mapping(source = "temperature", target = "temperature")
    GetWeatherResponse map(String temperature);
}
