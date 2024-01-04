package com.udemy.jetweatherapp.Screens.FavoriteScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.udemy.jetweatherapp.Models.Favorite
import com.udemy.jetweatherapp.Navigation.WeatherScreens
import com.udemy.jetweatherapp.Widgets.WeatherAppBar


@Composable
fun FavoritesScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()

){
    Scaffold (
        topBar = {
            WeatherAppBar(
                title = "Favorites",
                navController = navController,
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
            ){
                navController.popBackStack()
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground

    ) {
        innerPadding ->
        FavoritesContent(navController, favoriteViewModel,innerPadding)
    }
}

@Preview
@Composable
fun FavoritesContent(
    navController: NavController? = null,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    innerPadding: PaddingValues = PaddingValues(1.dp)
) {
    Column (
        modifier = Modifier
            .padding(top = 75.dp, start = 3.dp, end = 3.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ){

        val list = favoriteViewModel.favList.collectAsState().value

        LazyColumn {
            items(items = list){
                favorite ->
                CityCard(favorite, navController = navController, favoriteViewModel )
            }
        }

    }

}

@Composable
fun CityCard(
    favorite: Favorite,
    navController: NavController?,
    favoriteViewModel: FavoriteViewModel
) {
    Card (
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Transparent)
            .clickable {
                // Navigates back to main screen and sets the city name in the Ui and gets the weather
                   navController?.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
            },
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
            // City
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = favorite.city,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            )
            // Country
            Text(
                modifier = Modifier.padding(5.dp),
                text = favorite.country,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            )

            IconButton(
                onClick = {
                    favoriteViewModel.deleteFavorite(favorite)
                }
            ) {
                // Delete Icon
                Icon(
                    modifier = Modifier.padding(end = 15.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Icon",
                    tint = Color.Red.copy(alpha = 0.8f),
                )
            }

        }

    }

}
