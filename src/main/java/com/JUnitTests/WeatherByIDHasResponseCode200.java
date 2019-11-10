package com.JUnitTests;

import com.OpenWeather.OpenWeatherMethods;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherByIDHasResponseCode200 {

    @ParameterizedTest
    @MethodSource
    void currentWeatherByID(int input) {
        JSONObject response = new JSONObject(OpenWeatherMethods.currentWeatherByID(input).getBody().asString());
        System.out.println("Weather By ID Has Response Code 200: Testing ID: "+ input);
        assertEquals(input,response.get("id"),"For ID: "+input);
        assertEquals(200, response.get("cod"),"For ID: "+ input);

    }
    private static ArrayList<Integer> currentWeatherByID(){
        ArrayList<Integer> returnList = new ArrayList<>();
            returnList.add(593116);
            returnList.add(756135);
            returnList.add(2950159);
            returnList.add(2988507);
            returnList.add(2643743);
            returnList.add(524901);
            returnList.add(6539761);
        return returnList;
    }
}