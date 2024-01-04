package com.udemy.jetweatherapp.Widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.udemy.jetweatherapp.Models.Weather
import com.udemy.jetweatherapp.Models.WeatherItem
import com.udemy.jetweatherapp.Utils.formatDay
import com.udemy.jetweatherapp.Utils.formatDecimals


@Composable
fun WeatherCard(
    item: WeatherItem,
    imageUrl: String = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png"
){

    Card (
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Transparent),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ){

        Row (
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            // Day of the week
            Text(
                modifier = Modifier.padding(5.dp),
                text = formatDay(item.dt),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
            )

            // Weather Icon
            WeatherStateImage(imageUrl = imageUrl)

            // Weather Description
            Text(
                modifier = Modifier
                    .padding(5.dp),
                text = item.weather[0].description,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            )

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                // Max Temp
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = formatDecimals(item.temp.max) + "°",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Blue,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                )
                // Min Temp
                Text(
                    modifier = Modifier.padding(2.dp),
                    text =  formatDecimals(item.temp.min) + "°",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                )
            }
        }
    }
}