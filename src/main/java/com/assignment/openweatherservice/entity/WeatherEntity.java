package com.assignment.openweatherservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weather")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherEntity {
    @Id
    @GeneratedValue(generator = "seq_weather")
    private long id;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "temperature")
    private String temperature;
}
