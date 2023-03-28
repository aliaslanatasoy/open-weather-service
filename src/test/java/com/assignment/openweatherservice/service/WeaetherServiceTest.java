package com.assignment.openweatherservice.service;

import com.assignment.openweatherservice.config.TestRedisConfiguration;
import com.assignment.openweatherservice.entity.WeatherEntity;
import com.assignment.openweatherservice.exception.ApiWeatherServiceException;
import com.assignment.openweatherservice.exception.WeatherStackServiceNotAvailableException;
import com.assignment.openweatherservice.mapper.GetWeatherResponseMapper;
import com.assignment.openweatherservice.mapper.WeatherEntityMapper;
import com.assignment.openweatherservice.model.GetWeatherResponse;
import com.assignment.openweatherservice.model.weatherstack.Current;
import com.assignment.openweatherservice.model.weatherstack.Error;
import com.assignment.openweatherservice.model.weatherstack.WeatherStackResponse;
import com.assignment.openweatherservice.repository.WeatherRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ContextConfiguration(initializers = {WeaetherServiceTest.Initializer.class})
public class WeaetherServiceTest {
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");
    @Autowired
    private WeatherService weatherService;
    @MockBean
    private WeatherStackService weatherStackService;
    @MockBean
    private GetWeatherResponseMapper getWeatherResponseMapper;
    @MockBean
    private WeatherEntityMapper weatherEntityMapper;
    @MockBean
    private WeatherRepository weatherRepository;

    @Test
    public void whenGetWeatherCalled_saveCache_thenReturnValue() {
        WeatherStackResponse weatherStackResponse = new WeatherStackResponse();
        Current current = new Current();
        current.setTemperature("15");
        weatherStackResponse.setCurrent(current);
        GetWeatherResponse getWeatherResponse = new GetWeatherResponse();
        getWeatherResponse.setTemperature("15");

        WeatherEntity weatherEntity = WeatherEntity
                .builder().temperature("15").cityName("someCity").build();

        when(weatherRepository.save(any())).thenReturn(weatherEntity);
        when(weatherStackService.getWeather(anyString())).thenReturn(weatherStackResponse);
        when(getWeatherResponseMapper.map(anyString())).thenReturn(getWeatherResponse);
        when(weatherEntityMapper.map(anyString(), anyString())).thenReturn(weatherEntity);


        GetWeatherResponse cacheMissResponse = weatherService.getWeather("someCity");
        GetWeatherResponse cacheHitResponse = weatherService.getWeather("someCity");

        verify(weatherStackService, times(1)).getWeather(anyString());

    }

    @Test
    public void whenAccessKeyMissing_WeatherStackServiceReturnsError_throwApiWeatherException() {
        WeatherStackResponse weatherStackResponse = new WeatherStackResponse();
        Error error = new Error();
        error.setInfo("SomeInfo");
        error.setCode("someCode");
        weatherStackResponse.setError(error);

        when(weatherStackService.getWeather(anyString())).thenReturn(weatherStackResponse);

        assertThrows(ApiWeatherServiceException.class, () -> weatherService.getWeather("someErrorCausingCity"));
    }

    @Test
    public void whenResponseIsNull_throwOpenWeatherServiceNotAvailableException() {
        when(weatherStackService.getWeather(anyString())).thenReturn(new WeatherStackResponse());

        assertThrows(WeatherStackServiceNotAvailableException.class, () -> weatherService.getWeather("nonExistentCity"));
        verify(weatherStackService, times(1)).getWeather("nonExistentCity");
        verifyNoInteractions(weatherRepository);
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
