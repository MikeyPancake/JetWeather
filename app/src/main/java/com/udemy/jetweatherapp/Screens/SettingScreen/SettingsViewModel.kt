package com.udemy.jetweatherapp.Screens.SettingScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemy.jetweatherapp.Models.Favorite
import com.udemy.jetweatherapp.Models.Unit
import com.udemy.jetweatherapp.Repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(

    private val repository: WeatherDBRepository

) : ViewModel(){

    private val _unitList = MutableStateFlow<List<Unit>> (emptyList())

    // this value can be accessed from anyone
    val unitList = _unitList.asStateFlow() // assigns the data to the public variable

    init {
        viewModelScope.launch (Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                // collects the data
                .collect(){
                    listOfUnits ->
                    if(listOfUnits.isEmpty()){
                        Log.e("Units View Model", "Empty Units list")
                    }
                    else{
                        _unitList.value = listOfUnits // Takes collected units and assigns to private list
                        Log.d("Units View Model", " Unit List items: ${unitList.value}")
                    }
                }
        }
    }

    /**
     * These are the functions we call when we want to perform actions to our database
     */
    // When view Model is called, here we insert the unit to the DB through the repo
    fun insertUnit(unit: Unit) = viewModelScope.launch {
        repository.insertUnit(unit)
    }

    fun updateUnit(unit: Unit) = viewModelScope.launch {
        repository.updateUnit(unit)
    }

    fun deleteUnit(unit: Unit) = viewModelScope.launch {
        repository.deleteUnit(unit)
    }

    fun deleteAllUnit() = viewModelScope.launch {
        repository.deleteAllUnits()
    }


}