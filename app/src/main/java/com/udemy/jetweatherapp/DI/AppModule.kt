package com.udemy.jetweatherapp.DI

import android.content.Context
import androidx.room.Room
import com.udemy.jetweatherapp.Data.WeatherDao
import com.udemy.jetweatherapp.Data.WeatherDatabase
import com.udemy.jetweatherapp.Network.WeatherAPI
import com.udemy.jetweatherapp.Utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides the Dao
     */
    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase) : WeatherDao
    = weatherDatabase.weatherDao()

    /**
     * Create the database
     */
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase
    = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weather_database")
        .fallbackToDestructiveMigration()
        .build()

    /**
     * Dagger/Hilt will call this function in the background
     */
    @Singleton
    @Provides
    fun provideOpenWeatherApi(): WeatherAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
}