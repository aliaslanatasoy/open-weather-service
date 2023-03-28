package com.assignment.openweatherservice.model.weatherstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Current {
    @JsonProperty("observation_time")
    private String observationTime;
    private String temperature;
    @JsonProperty("weather_code")
    private String weatherCode;
    @JsonProperty("weather_icons")
    private String[] weatherIcons;
    @JsonProperty("weather_descriptions")
    private String[] weatherDescriptions;
    @JsonProperty("wind_speed")
    private String windSpeed;
    @JsonProperty("wind_degree")
    private String windDegree;
    @JsonProperty("wind_dir")
    private String windDir;
    private String pressure;
    @JsonProperty("precip")
    private String precipitation;
    private String humidity;
    @JsonProperty("cloudcover")
    private String cloudCover;
    @JsonProperty("feelslike")
    private String feelsLike;
    @JsonProperty("uv_index")
    private String uvIndex;
    private String visibility;
    @JsonProperty("is_day")
    private String isDay;
}
