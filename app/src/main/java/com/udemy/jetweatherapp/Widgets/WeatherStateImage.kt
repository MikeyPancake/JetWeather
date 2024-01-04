package com.udemy.jetweatherapp.Widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        modifier = Modifier.size(80.dp),
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = "Icon Image")
}