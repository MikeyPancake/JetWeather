package com.udemy.jetweatherapp.Data

/**
 * This allows us to send extra data with the information we receive
 * this is also known as a wrapper class
 */
class DataOrException <T, Boolean, E: Exception> (

    var data : T? = null, // data can be of any type
    var loading : Boolean? = null,
    var e : E? = null
){
}