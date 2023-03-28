package com.assignment.openweatherservice.mapper;

import com.assignment.openweatherservice.entity.WeatherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeatherEntityMapper {
    @Mapping(source = "city", target = "cityName")
    @Mapping(source = "temperature", target = "temperature")
    WeatherEntity map(String city, String temperature);
}
