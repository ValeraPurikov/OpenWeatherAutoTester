package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByNameHasCorrectCountry {

    static OpenWeatherMethods weatherMethods;

    @BeforeAll
    static void init(TestInfo info) {
        //Arrange
        weatherMethods = new OpenWeatherMethods();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Weather By Name Has Correct Country")
    void currentWeatherByName(String expected, String input) {
        //Act
        JSONObject response = new JSONObject(weatherMethods.currentWeatherByName(input).getBody().asString());
        //Assert
        assertTrue(response.has("sys"), "Response has no sys key!");
        assertTrue(response.getJSONObject("sys").has("country"), "Response has no country key!");
        assertEquals(input, response.get("name"), "For City: " + input);
        assertEquals(expected, response.getJSONObject("sys").get("country"), "For City: " + input);
    }

    private static Stream<Arguments> currentWeatherByName() {
        return Stream.of(
                Arguments.of("LT", "Vilnius"),
                Arguments.of("PL", "Warsaw"),
                Arguments.of("DE", "Berlin"),
                Arguments.of("FR", "Paris"),
                Arguments.of("GB", "London"),
                Arguments.of("RU", "Moscow"),
                Arguments.of("IT", "Rome"),
                Arguments.of("IT", "Morino")
        );
    }
}