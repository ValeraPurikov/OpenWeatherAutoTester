package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByNameHasResponseCode200 {

    static OpenWeatherMethods weatherMethods;

    @BeforeAll
    static void init(TestInfo info) {
        //Arrange
        weatherMethods = new OpenWeatherMethods();
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("Weather By Name Has Response Code 200")
    void currentWeatherByName(String input) {
        //Act
        JSONObject response = new JSONObject(weatherMethods.currentWeatherByName(input).getBody().asString());
        //Assert
        assertEquals(input, response.get("name"), "For City: " + input);
        assertEquals(200, response.get("cod"), "For City: " + input);
    }

    private static ArrayList<String> currentWeatherByName() {
        ArrayList<String> returnList = new ArrayList<>();
        returnList.add("Vilnius");
        returnList.add("Warsaw");
        returnList.add("Berlin");
        returnList.add("Paris");
        returnList.add("London");
        returnList.add("Moscow");
        returnList.add("Rome");
        returnList.add("Marino");
        return returnList;
    }
}