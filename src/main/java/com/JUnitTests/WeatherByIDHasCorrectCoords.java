package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByIDHasCorrectCoords {

    static OpenWeatherMethods weatherMethods;

    @BeforeAll
    static void init(TestInfo info) {
        //Arrange
        weatherMethods = new OpenWeatherMethods();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Weather By ID Has Correct Coords")
    void currentWeatherByID(double expectedLon, double expectedLat, int input) {
        //Act
        JSONObject response = new JSONObject(weatherMethods.currentWeatherByID(input).getBody().asString());
        //Assert
        assertTrue(response.has("coord"), "Response has no coords!");
        assertEquals(input, response.get("id"), "For ID: " + input);
        assertEquals(expectedLon, response.getJSONObject("coord").get("lon"), "For ID: " + input);
        assertEquals(expectedLat, response.getJSONObject("coord").get("lat"), "For ID: " + input);
    }

    private static Stream<Arguments> currentWeatherByID() {
        return Stream.of(
                Arguments.of(25.28, 54.69, 593116),
                Arguments.of(21.01, 52.23, 756135),
                Arguments.of(13.41, 52.52, 2950159),
                Arguments.of(2.35, 48.85, 2988507),
                Arguments.of(-0.13, 51.51, 2643743),
                Arguments.of(37.62, 55.75, 524901),
                Arguments.of(12.64, 41.76, 6539761),
                Arguments.of(12.64, 41.76, 6539761)
        );
    }
}