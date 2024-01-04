package com.udemy.jetweatherapp.Repository

import android.util.Log
import com.udemy.jetweatherapp.Data.DataOrException
import com.udemy.jetweatherapp.Models.Weather
import com.udemy.jetweatherapp.Network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api : WeatherAPI) {

    suspend fun getWeather(cityQuery: String, units: String)
    : DataOrException<Weather, Boolean, Exception> {
        val response =
            try {
                api.getWeather(query = cityQuery, units = units)
            }catch (e : Exception){
                // loads the wrapper class with the exception
                Log.e("Repository", "getWeather Exception: $e")
                return DataOrException(e = e)
            }
        Log.d("Repository", "getWeather: $response")
        return DataOrException(data = response)

    }



}