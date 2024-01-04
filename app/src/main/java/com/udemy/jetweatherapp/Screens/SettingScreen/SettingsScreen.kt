package com.udemy.jetweatherapp.Screens.SettingScreen

import android.util.Log
import android.widget.Button
import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.udemy.jetweatherapp.Models.Unit
import com.udemy.jetweatherapp.Navigation.WeatherScreens
import com.udemy.jetweatherapp.Widgets.WeatherAppBar

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()

){

    Scaffold (
        topBar = {
            WeatherAppBar(
                title = "Settings",
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
        SettingsContent(navController, settingsViewModel, innerPadding)

    }
}

@Preview
@Composable
fun SettingsContent(
    navController: NavController? = null,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    innerPadding: PaddingValues = PaddingValues(1.dp)
) {

    // Handles toggle state function
    var unitToggleState by remember{
        mutableStateOf(false)
    }

    val measurementUnits = listOf("Imperial (F)", "Metric (C)")

    // Gets unit choice from DB
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value

    // if db contains no value, set default to Imperial
    val defaultChoice = if (choiceFromDb.isNullOrEmpty()) measurementUnits[0]
    else choiceFromDb[0].unit

    // sets the default choice to the choice state
    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }

    Column (
        modifier = Modifier
            .padding(top = 75.dp, start = 3.dp, end = 3.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier.padding(bottom = 15.dp),
            text = "Change Unit of Measurements",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
        )

        IconToggleButton(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .clip(MaterialTheme.shapes.medium)
                .padding(5.dp)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            checked = !unitToggleState ,
            onCheckedChange = {

                // sets state to selected measurement
                unitToggleState = !it
                // Toggle the choice state when pressed
                choiceState =
                    if(unitToggleState){
                        "Imperial (F)"
                    } else {
                        "Metric (C)"
                    }
            }
        ) {
            Text(
                text = if (unitToggleState){ "Fahrenheit °F" }
                else { "Celsius °C" }
            )
            Log.d("Setting Screen", "Selected State: $unitToggleState")
            Log.d("Setting Screen", "Choice Unit: $choiceState")
            Log.d("Setting Screen", "Db Unit: ${settingsViewModel.unitList.collectAsState().value}")


        }

        ElevatedButton(
            modifier = Modifier.padding(10.dp),
            onClick = {
                // Update units table with selected unit
                settingsViewModel.deleteAllUnit() // removes previous unit
                settingsViewModel.insertUnit(Unit(unit = choiceState))// inserts unit from choice state
                navController?.popBackStack()
            },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 5.dp,
                pressedElevation = 10.dp
            )
        ) {
            Text(
                modifier = Modifier.padding(1.dp),
                text = "Save",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            )
            
        }

    }

}
