package com.udemy.jetweatherapp.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udemy.jetweatherapp.Models.Favorite
import com.udemy.jetweatherapp.Models.Unit
import kotlinx.coroutines.flow.Flow

/**
 * This is where all CRUD activities are done. The Dao interacts directly with the room DB
 */

@Dao
interface WeatherDao {

    // When the function is called, room will use the Query to get all from table
    @Query("SELECT * from fav_tbl")
    fun getFavorites(): Flow<List<Favorite>> // Returns a list in stream flow

    // Allows query to run in the background
    @Query("SELECT * from fav_tbl where city =:city")
    suspend fun getFavById(city: String): Favorite

    // If fav already exists, replace the previous instance
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFav(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFav()

    @Delete
    suspend fun deleteFav(favorite: Favorite)

    // Units //

    @Query("SELECT * from settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE from settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)

}