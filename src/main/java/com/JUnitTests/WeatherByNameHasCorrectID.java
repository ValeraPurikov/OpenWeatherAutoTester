package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByNameHasCorrectID {

    @ParameterizedTest
    @MethodSource
    void currentWeatherByName(String input,int expected) {
        JSONObject response = new JSONObject(OpenWeatherMethods.currentWeatherByName(input).getBody().asString());
        System.out.println("Weather By Name Has Correct ID: Testing City: "+ input);
        assertEquals(input,response.get("name"),"For City: "+input);
        assertEquals(expected,response.get("id"),"For City: "+input);
    }
    private static Stream<Arguments> currentWeatherByName(){
        return Stream.of(
                Arguments.of("Vilnius", 593116),
                Arguments.of("Warsaw", 756135),
                Arguments.of("Berlin", 2950159),
                Arguments.of("Paris", 2988507),
                Arguments.of("London", 2643743),
                Arguments.of("Moscow", 524901),
                Arguments.of("Rome", 6539761),
                Arguments.of("Marino", 6539761)
        );
    }
}