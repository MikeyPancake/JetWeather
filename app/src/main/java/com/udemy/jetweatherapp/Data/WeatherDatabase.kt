package com.udemy.jetweatherapp.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udemy.jetweatherapp.Models.Favorite
import com.udemy.jetweatherapp.Models.Unit


@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}