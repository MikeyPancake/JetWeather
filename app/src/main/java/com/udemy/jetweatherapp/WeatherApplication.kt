package com.udemy.jetweatherapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Ties everything together for Dependency Injection using Hilt which is registered in the
 * manifest file using .WeatherApplication
 */

@HiltAndroidApp
class WeatherApplication : Application() {}