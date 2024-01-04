package com.udemy.jetweatherapp.Repository

import com.udemy.jetweatherapp.Data.WeatherDao
import com.udemy.jetweatherapp.Models.City
import com.udemy.jetweatherapp.Models.Favorite
import com.udemy.jetweatherapp.Models.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This repository is responsible for the Room Database interactions through the Dao
 */

// injects the weather dao so it can be used to perform actions
class WeatherDBRepository @Inject constructor(private val weatherDao:WeatherDao) {

    /**
     * These functions are called from the activity and the repo then calls the Dao functions to
     * get/insert/update/delete data
     */
    fun getFavorites() : Flow<List<Favorite>> = weatherDao.getFavorites() // access DB through the Dao

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFav(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFav(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFav()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFav(favorite)
    suspend fun getFavById(city: String) : Favorite = weatherDao.getFavById(city)


    fun getUnits() : Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)


}