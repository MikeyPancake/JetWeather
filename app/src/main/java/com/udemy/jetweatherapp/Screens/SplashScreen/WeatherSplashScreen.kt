package com.udemy.jetweatherapp.Screens.SplashScreen


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.scale
import com.udemy.jetweatherapp.Navigation.WeatherScreens
import com.udemy.jetweatherapp.R
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController){

    val defaultCity = "Atlanta"
    // Initial state value for Image
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(
        key1 = true,
        block = {
            scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                OvershootInterpolator(8f).getInterpolation(it) }
            ))
            delay(2000L)
            navController.navigate(WeatherScreens.MainScreen.name + "/$defaultCity")
        }
    )



    Surface (
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(
            width = 2.dp, 
            color = MaterialTheme.colorScheme.outline
        )

    ) {
        Column (
            modifier = Modifier.padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                Icons.Outlined.WbSunny,
                contentDescription = "Logo",
                modifier = Modifier.size(95.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )

//            Image(
//                modifier = Modifier.size(95.dp),
//                painter = painterResource(id = R.drawable.sun),
//                contentDescription = "Logo",
//                contentScale = ContentScale.Fit
//            )

            Text(
                style = MaterialTheme.typography.displaySmall,
                text = "Find the Sun?",
                color = MaterialTheme.colorScheme.onBackground

            )
            
        }

    }
}