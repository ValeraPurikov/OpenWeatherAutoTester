package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByZipHasResponseCode200 {

    @ParameterizedTest
    @MethodSource
    void currentWeatherByZip(int input) {
        JSONObject response = new JSONObject(OpenWeatherMethods.currentWeatherByZip(input).getBody().asString());
        System.out.println("Weather By Zip Has Response Code 200: Testing Zip: "+ input);
        assertEquals(200,response.get("cod"),"For Zip: "+input);
    }
    private static ArrayList<Integer> currentWeatherByZip(){
        ArrayList<Integer> returnList = new ArrayList<>();
        returnList.add(15100);
        returnList.add(14100);
        returnList.add(13000);
        returnList.add(11111);
        returnList.add(10000);
        returnList.add(17123);
        returnList.add(18262);
        returnList.add(11233);
        return returnList;
    }
}