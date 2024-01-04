package com.udemy.jetweatherapp.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udemy.jetweatherapp.Screens.AboutScreen.AboutScreen
import com.udemy.jetweatherapp.Screens.FavoriteScreen.FavoritesScreen
import com.udemy.jetweatherapp.Screens.MainScreen.MainScreen
import com.udemy.jetweatherapp.Screens.MainScreen.MainViewModel
import com.udemy.jetweatherapp.Screens.SearchScreen.SearchScreen
import com.udemy.jetweatherapp.Screens.SettingScreen.SettingsScreen
import com.udemy.jetweatherapp.Screens.SplashScreen.WeatherSplashScreen

@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ){
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }
        /**
         * We create the route variable so that we can call the route and add extra data like the city
         * This helps us when we navigate from the search screen back to the main screen
         *
         * This is how you pass data from one composable to another!!!!
         */
        val route = WeatherScreens.MainScreen.name // Define the route
        composable(
            "$route/{city}", // call the route with the appended value of city
            arguments = listOf( // Create a list of values (arguments)
                navArgument(name = "city"){
                    type = NavType.StringType // defines the type of data we are passing
                }
        ) ){
            navBack ->
            // Bundle the arguments based on the key we defined above
            navBack.arguments?.getString("city").let { // it is the data we are passing (city)
                city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city)
            }


        }
        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.FavoritesScreen.name){
            FavoritesScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name){
            SettingsScreen(navController = navController)
        }

    }
}