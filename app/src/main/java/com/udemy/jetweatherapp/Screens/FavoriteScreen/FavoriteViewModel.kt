package com.udemy.jetweatherapp.Screens.FavoriteScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemy.jetweatherapp.Models.Favorite
import com.udemy.jetweatherapp.Repository.WeatherDBRepository
import com.udemy.jetweatherapp.Repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(

    private val repository: WeatherDBRepository

) : ViewModel(){

    private val _favList = MutableStateFlow<List<Favorite>> (emptyList())

    // this value can be accessed from anyone
    val favList = _favList.asStateFlow() // assigns the data to the public variable

    init {
        viewModelScope.launch (Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                // collects the data
                .collect(){listOfFavs ->
                    if(listOfFavs.isNullOrEmpty()){
                        Log.e("Favorites View Model", "Empty Favorites list")
                    }
                    else{
                        _favList.value = listOfFavs // Takes collected favs and assigns to private list
                        Log.d("Favorites View Model", " Favs List items: ${favList.value}")
                    }

                }
        }
    }

    /**
     * These are the functions we call when we want to perform actions to our database
     */
    // When view Model is called, here we insert the favorite to the DB through the repo
    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.insertFavorite(favorite)
    }

    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.updateFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favorite)
    }

    fun deleteAllFavorites() = viewModelScope.launch {
        repository.deleteAllFavorites()
    }


}