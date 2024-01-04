package com.udemy.jetweatherapp.Screens.MainScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.udemy.jetweatherapp.Data.DataOrException
import com.udemy.jetweatherapp.Models.Weather
import com.udemy.jetweatherapp.Models.WeatherItem
import com.udemy.jetweatherapp.Navigation.WeatherScreens
import com.udemy.jetweatherapp.R
import com.udemy.jetweatherapp.Screens.SettingScreen.SettingsViewModel
import com.udemy.jetweatherapp.Utils.formatDate
import com.udemy.jetweatherapp.Utils.formatDateTime
import com.udemy.jetweatherapp.Utils.formatDecimals
import com.udemy.jetweatherapp.Widgets.WeatherAppBar
import com.udemy.jetweatherapp.Widgets.WeatherCard
import com.udemy.jetweatherapp.Widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
){
    // this will prevent crashes for first time users
    val currentCity : String = if(city!!.isBlank())"New York" else city

    // gets measurement from Db
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    Log.d("Main Screen", "Units from Db: $unitFromDb")

    var unit by remember{
        mutableStateOf("imperial")
    }

    var isImperial by remember{
        mutableStateOf(false)
    }

    // if unit is not null or empty
    if (!unitFromDb.isNullOrEmpty()){
        // sets unit state to the unit from the db
        unit = unitFromDb[0].unit.split(" ")[0].lowercase() // splits Imperial (F) and get Imperial in lowercase
        isImperial = unit == "imperial"

        // Gets city from the database when the app is started
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)) {
            // uses the city argument passed to the composable from the navigator
            value = mainViewModel.getWeatherData(
                city = currentCity,
                units = unit
            )
        }.value

        if (weatherData.loading == true){
            CircularProgressIndicator()

        }else if (weatherData.data != null){
            // Once loading is complete, call the Main Scaffold and pass the data to it
            MainScaffold(weatherData.data!!, navController, isImperial)
        }
    }
}

@Composable
fun MainScaffold(
    weatherData : Weather,
    navController: NavController,
    isImperial : Boolean
) {

    Scaffold (
        topBar = {
            WeatherAppBar(
                title = weatherData.city.name + ", ${weatherData.city.country}",
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)

                }

            ){
                Log.d("App Bar", "MainScaffold: Button Clicked")
            }
                 },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground

    ) {
        innerPadding ->
        MainContent(data = weatherData, innerPadding, isImperial)
    }
}


@Composable
fun MainContent(
    data: Weather,
    innerPadding: PaddingValues,
    isImperial : Boolean,
    imageUrl: String = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png",
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Date Information
        Text(
            modifier = Modifier.padding(5.dp),
            text =  formatDate(data.list[0].dt),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight
        )
        // Call Main Graphic composable
        WeatherGraphic(imageUrl, data)

        // Sets the Atmospherics card in the UI
        AtmosphericsCard(data, isImperial)

        Text(
            modifier = Modifier.padding(5.dp),
            text = "This Week's Weather Forecast",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
        )

        Divider(
            modifier = Modifier
                .padding(2.dp)
                .width(100.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        // Sets the lazy Column that contains the 7 day forecast
        WeeklyForecast(data)
    }
}

@Composable
private fun AtmosphericsCard(data: Weather, isImperial : Boolean) {
    Card(
        modifier = Modifier.padding(5.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        // Calls composable to set row
        HumidityWindPressureRow(data, isImperial)

        Divider(
            modifier = Modifier.padding(5.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        // Calls composable to set row
        SunsetSunriseRow(data)
    }
}

@Composable
fun WeeklyForecast(data: Weather) {

    LazyColumn (
        modifier = Modifier.padding(2.dp),
        contentPadding = PaddingValues(1.dp)
    ) {
        items (items = data.list){
            item: WeatherItem ->
            WeatherCard(item)
        }
    }
}

@Composable
fun SunsetSunriseRow(data: Weather) {

    Row (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        // Humidity Row
        Row (
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = rememberAsyncImagePainter(R.drawable.sunrise),
                contentDescription = "Sun Rise icon")
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = formatDateTime(data.list[0].sunrise),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
            )
        }

        // Pressure Row
        Row (
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = formatDateTime(data.list[0].sunset),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
            )
            Image(
                modifier = Modifier.size(20.dp),
                painter = rememberAsyncImagePainter(R.drawable.sunset),
                contentDescription = "Sun Set icon")

        }
    }
}

@Composable
fun HumidityWindPressureRow(data: Weather, isImperial : Boolean){
    Row (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        // Humidity Row
        Row (
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = rememberAsyncImagePainter(R.drawable.humidity),
                contentDescription = "Humidity icon")
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = data.list[0].humidity.toString() + " %",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
            )
        }

        // Pressure Row
        Row (
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = rememberAsyncImagePainter(R.drawable.pressure),
                contentDescription = "Pressure icon")
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = data.list[0].pressure.toString() + " psi",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
            )
        }

        // Wind Speed Row
        Row (
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier.size(20.dp),
                painter = rememberAsyncImagePainter(R.drawable.wind),
                contentDescription = "Wind icon")
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = formatDecimals(data.list[0].speed)  + if (isImperial)" mph" else " m/s",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
            )
        }
    }
}

@Composable
private fun WeatherGraphic(imageUrl: String, data: Weather) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
        shape = CircleShape,
        color = Color(0xFFFFC400) // Color(0xFFFFC400)
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Sets the image in the circle using the url and coil to retrieve the image
            WeatherStateImage(imageUrl = imageUrl)

            Text(
                text = "${data.list[0].temp.day.toInt()}°",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = MaterialTheme.typography.displaySmall.fontWeight
            )
            Text(
                text = data.list[0].weather[0].main,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = MaterialTheme.typography.displaySmall.fontWeight
            )
        }
    }
}


