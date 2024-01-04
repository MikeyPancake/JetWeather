package com.udemy.jetweatherapp.Screens.AboutScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.udemy.jetweatherapp.Navigation.WeatherScreens
import com.udemy.jetweatherapp.R
import com.udemy.jetweatherapp.Utils.formatDate
import com.udemy.jetweatherapp.Widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController){

    Scaffold (
        topBar = {
            WeatherAppBar(
                title = "About",
                navController = navController,
                isMainScreen = false,
                icon = Icons.Default.ArrowBack,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.MainScreen.name)

                }
            ){
                navController.popBackStack()
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground

    ) {
        innerPadding ->
        MainContent(innerPadding)
    }

}

@Composable
fun MainContent(
    innerPadding: PaddingValues
){
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface

    ) {
        Column (
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text =  stringResource(id = R.string.about_app),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                modifier = Modifier.padding(5.dp),
                text =  stringResource(id = R.string.api_used),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = MaterialTheme.typography.headlineSmall.fontWeight
            )
        }

    }

}