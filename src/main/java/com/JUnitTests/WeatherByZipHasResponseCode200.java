package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByZipHasResponseCode200 {

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
    @DisplayName("Weather By Zip Has Response Code 200")
    void currentWeatherByZip(int input) {
        JSONObject response = new JSONObject(weatherMethods.currentWeatherByZip(input).getBody().asString());
        System.out.println("Testing Zip: "+ input);
        assertEquals(200,response.get("cod"),"For Zip: "+input);
    }
    private static ArrayList<Integer> currentWeatherByZip(){
        ArrayList<Integer> returnList = new ArrayList<>();
        returnList.add(17123);
        returnList.add(11233);
        return returnList;
    }
}