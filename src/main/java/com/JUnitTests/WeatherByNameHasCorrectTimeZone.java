package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByNameHasCorrectTimeZone {


    @ParameterizedTest
    @MethodSource
    void currentWeatherByName(int expected, String input) {
        JSONObject response = new JSONObject(OpenWeatherMethods.currentWeatherByName(input).getBody().asString());
        System.out.println("Weather By Name Has Correct Time Zone: Testing City: "+ input);
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