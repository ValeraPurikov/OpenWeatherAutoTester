package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByCoordsHasResponseCode200 {

    static OpenWeatherMethods weatherMethods;

    @BeforeAll
    static void init(TestInfo info) {
        //Arrange
        weatherMethods = new OpenWeatherMethods();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Weather By Coords Has Response Code 200")
    void currentWeatherByCoords(int lon, int lat) {
        //Act
        JSONObject response = new JSONObject(weatherMethods.currentWeatherByCoords(lon, lat).getBody().asString());
        //Assert
        assertTrue(response.has("coord"), "Response Has No Coords!");
        assertEquals(lon, response.getJSONObject("coord").get("lon"), "For Cords: " + lon + ":" + lat);
        assertEquals(lat, response.getJSONObject("coord").get("lat"), "For Cords: " + lon + ":" + lat);
        assertEquals(200, response.get("cod"), "For Coords: " + lon + ":" + lat);
    }

    private static Stream<Arguments> currentWeatherByCoords() {
        return Stream.of(
                Arguments.of(1, 1),
                Arguments.of(5, 5),
                Arguments.of(10, 10),
                Arguments.of(15, 15),
                Arguments.of(20, 20),
                Arguments.of(25, 25),
                Arguments.of(30, 30),
                Arguments.of(35, 35)
        );
    }
}