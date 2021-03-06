package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByIDHasCorrectTimeZone {

    static OpenWeatherMethods weatherMethods;

    @BeforeAll
    static void init(TestInfo info) {
        //Arrange
        weatherMethods = new OpenWeatherMethods();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Weather By ID Has Correct Time Zone")
    void currentWeatherByID(int expected, int input) {
        //Act
        JSONObject response = new JSONObject(weatherMethods.currentWeatherByID(input).getBody().asString());
        //Assert
        assertTrue(response.has("timezone"), "Response has no timezone!");
        assertEquals(input, response.get("id"), "For ID: " + input);
        assertEquals(expected, response.get("timezone"), "For ID: " + input);
    }

    private static Stream<Arguments> currentWeatherByID() {
        return Stream.of(
                Arguments.of(7200, 593116),
                Arguments.of(3600, 756135),
                Arguments.of(3600, 2950159),
                Arguments.of(3600, 2988507),
                Arguments.of(0, 2643743),
                Arguments.of(10800, 524901),
                Arguments.of(3600, 6539761),
                Arguments.of(3600, 6539761)
        );
    }
}