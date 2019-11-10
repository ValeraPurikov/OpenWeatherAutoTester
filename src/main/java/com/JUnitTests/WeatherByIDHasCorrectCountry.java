package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByIDHasCorrectCountry {
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
    @DisplayName("Weather By ID Has Correct Country")
    void currentWeatherByID(String expected,int input) {
        JSONObject response = new JSONObject(weatherMethods.currentWeatherByID(input).getBody().asString());
        System.out.println("Testing ID: "+ input);
        assertTrue(response.has("sys"), "Response has no sys key!");
        JSONObject temp = response.getJSONObject("sys");
        assertTrue(temp.has("country"), "Response has no country key!");
        assertEquals(input,response.get("id"),"For ID: "+input);
        assertEquals(expected,response.getJSONObject("sys").get("country"),"For ID: "+input);
    }
    private static Stream<Arguments> currentWeatherByID(){
        return Stream.of(
                Arguments.of("LT", 593116),
                Arguments.of("PL", 756135),
                Arguments.of("DE", 2950159),
                Arguments.of("FR", 2988507),
                Arguments.of("GB", 2643743),
                Arguments.of("RU", 524901),
                Arguments.of("IT", 6539761),
                Arguments.of("IT", 6539761)
        );
    }
}