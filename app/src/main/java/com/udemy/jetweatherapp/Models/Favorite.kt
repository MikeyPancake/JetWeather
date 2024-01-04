package com.udemy.jetweatherapp.Models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This sets this data class as a room model that is responsible for saving the data into a table
 */

@Entity(tableName = "fav_tbl")
data class Favorite(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "country")
    val country : String,

)
