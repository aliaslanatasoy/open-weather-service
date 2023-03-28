package com.assignment.openweatherservice.repository;

import com.assignment.openweatherservice.entity.WeatherEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends CrudRepository<WeatherEntity, Long> {

}
