package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByNameHasCorrectCoords {
    static OpenWeatherMethods weatherMethods;

    @BeforeAll
    static void init(TestInfo info) {
        //Arrange
        weatherMethods = new OpenWeatherMethods();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Weather By Name Has Correct Coords")
    void currentWeatherByName(double expectedLon, double expectedLat, String input) {
        //Act
        JSONObject response = new JSONObject(weatherMethods.currentWeatherByName(input).getBody().asString());
        //Assert
        assertTrue(response.has("coord"), "Response has no coords!");
        assertEquals(input, response.get("name"), "For City: " + input);
        assertEquals(expectedLon, response.getJSONObject("coord").get("lon"), "For City: " + input);
        assertEquals(expectedLat, response.getJSONObject("coord").get("lat"), "For City: " + input);
    }

    private static Stream<Arguments> currentWeatherByName() {
        return Stream.of(
                Arguments.of(25.28, 54.69, "Vilnius"),
                Arguments.of(21.01, 52.23, "Warsaw"),
                Arguments.of(13.41, 52.52, "Berlin"),
                Arguments.of(2.35, 48.85, "Paris"),
                Arguments.of(-0.13, 51.51, "London"),
                Arguments.of(37.62, 55.75, "Moscow"),
                Arguments.of(12.64, 41.76, "Rome"),
                Arguments.of(12.66, 41.76, "Marino")
        );
    }
}