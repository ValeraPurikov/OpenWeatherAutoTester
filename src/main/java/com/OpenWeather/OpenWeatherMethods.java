package com.OpenWeather;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OpenWeatherMethods {
    String API_KEY = "1c8582e83a1bbe87a345a5743d460dcf";


    public Response currentWeatherByName(String City){
       return RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q="+City+"&APPID="+API_KEY);
    }
    public Response currentWeatherByID(int ID){
        return RestAssured.get("http://api.openweathermap.org/data/2.5/weather?id="+ID+"&APPID="+API_KEY);
    }
    public Response currentWeatherByCoords(int lon, int lat){
        return RestAssured.get("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID="+API_KEY);
    }
    public Response currentWeatherByZip(int zip){
        return RestAssured.get("http://api.openweathermap.org/data/2.5/weather?zip="+zip+"&APPID="+API_KEY);
    }

}
