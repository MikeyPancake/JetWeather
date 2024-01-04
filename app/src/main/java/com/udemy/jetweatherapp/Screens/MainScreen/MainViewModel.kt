package com.udemy.jetweatherapp.Screens.MainScreen

import androidx.lifecycle.ViewModel
import com.udemy.jetweatherapp.Data.DataOrException
import com.udemy.jetweatherapp.Models.Weather
import com.udemy.jetweatherapp.Repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel()  {

    // Function that is called from the main screen which requires the city name
    suspend fun getWeatherData(city: String, units: String)
    // calls the repo function which then returns the weather data
    : DataOrException<Weather, Boolean, Exception>{
        return repository.getWeather(cityQuery = city, units = units)
    }


}