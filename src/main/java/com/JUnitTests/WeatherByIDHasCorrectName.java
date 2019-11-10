package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByIDHasCorrectName {

    @ParameterizedTest
    @MethodSource
    void currentWeatherByID(int input,String expected) {
        JSONObject response = new JSONObject(OpenWeatherMethods.currentWeatherByID(input).getBody().asString());
        System.out.println("Weather By ID Has Correct Name: Testing ID: "+ input);
        assertEquals(input,response.get("id"),"For ID: "+input);
        assertEquals(expected,response.get("name"),"For ID: "+input);
    }
    private static Stream<Arguments> currentWeatherByID(){
        return Stream.of(
                Arguments.of(593116, "Vilnius"),
                Arguments.of(756135, "Warsaw"),
                Arguments.of(2950159, "Berlin"),
                Arguments.of(2988507, "Paris"),
                Arguments.of(2643743, "London"),
                Arguments.of(524901, "Moscow"),
                Arguments.of(6539761, "Rome"),
                Arguments.of(6539761, "Marino")

        );
    }
}