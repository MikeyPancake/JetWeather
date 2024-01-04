package com.udemy.jetweatherapp.Utils

import java.text.SimpleDateFormat

fun formatDate(timestamp: Int) : String{
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatDecimals(item: Double): String {
    return " %.0f".format(item)
}

fun formatDay(timestamp: Int) : String{
    val sdf = SimpleDateFormat("EEE")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}