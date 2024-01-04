package com.udemy.jetweatherapp.Network

import com.udemy.jetweatherapp.Models.Weather
import com.udemy.jetweatherapp.Utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {

    // URL: https://api.openweathermap.org/data/2.5/forecast/daily?q=atlanta&units=imperial&appid=2d7fdd29d2e471e42b1e49966fff5a1b

    /**
     * When the getWeather function is called it will use the get and query values to form the api path.
     * The function then returns a weather object
     */
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(

        @Query(value = "q") query : String, //q=atlanta
        @Query(value = "units") units : String = "imperial", //units=imperial
        @Query("appid") appid : String = Constants.API_KEY //appid=2d7fdd29d2e471e42b1e49966fff5a1b
    ) : Weather
}