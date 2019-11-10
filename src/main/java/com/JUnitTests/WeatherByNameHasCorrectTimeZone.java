package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByNameHasCorrectTimeZone {
    static OpenWeatherMethods weatherMethods;
    static TestInfo testInfo;

    @BeforeAll
    static void init(TestInfo info){
        testInfo = info;
        weatherMethods = new OpenWeatherMethods();
        System.out.println("Running Test: "+ testInfo.getDisplayName());
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Weather By Name Has Correct Time Zone")
    void currentWeatherByName(int expected, String input) {
        JSONObject response = new JSONObject(weatherMethods.currentWeatherByName(input).getBody().asString());
        System.out.println("Testing City: "+ input);
        assertTrue(response.has("timezone"), "Response has no timezone!");
        assertEquals(input,response.get("name"),"For City: "+input);
        assertEquals(expected,response.get("timezone"),"For City: "+input);
    }
    private static Stream<Arguments> currentWeatherByName(){
        return Stream.of(
                Arguments.of(7200, "Vilnius"),
                Arguments.of(3600, "Warsaw"),
                Arguments.of(3600, "Berlin"),
                Arguments.of(3600, "Paris"),
                Arguments.of(0, "London"),
                Arguments.of(10800, "Moscow"),
                Arguments.of(3600, "Rome"),
                Arguments.of(3600, "Marino")

        );
    }
}