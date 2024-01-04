package com.udemy.jetweatherapp.Screens.SearchScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.udemy.jetweatherapp.Navigation.WeatherScreens
import com.udemy.jetweatherapp.Widgets.WeatherAppBar

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold (
        topBar = {
            WeatherAppBar(
                title = "Search A City",
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController,
                ){
                navController.popBackStack()
            }

        }

    ){
        innerPadding ->
        Surface (
            modifier = Modifier.padding(innerPadding)
        ) {
            Column (
                modifier = Modifier.padding(2.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchForm{searchResult ->
                    // This sends our search query
                    Log.d("Search Bar", " Search Query: $searchResult")
                    // On next, navigate back to main screen and send the search result to main ($searchResult)
                    navController.navigate(WeatherScreens.MainScreen.name + "/$searchResult")
                }



           }
       }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
){

    // Remember stateSaveable remembers the state when a device is rotated
    val searchQueryState = rememberSaveable{
        mutableStateOf("")
    }

    // gets the current devices keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    // validates that the user put in a text
    val valid = remember(searchQueryState.value){
        searchQueryState.value.trim().isNotEmpty()
    }

    Column {
        CommonTextField(
            valueState = searchQueryState,
            placeHolder = "Search",
            onAction = KeyboardActions {

                if (!valid) return@KeyboardActions
                // This is the else statements
                onSearch(searchQueryState.value.trim()) // Sends search value to onSearch
                searchQueryState.value = "" // Once next is clicked, clear text field
                keyboardController?.hide() // Hides keyboard
            }
        )
    }
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeHolder: String,
    keyboardType : KeyboardType = KeyboardType.Text,
    imeAction : ImeAction = ImeAction.Next, // When user clicks next, it will perform action
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        value = valueState.value,
        onValueChange = { valueState.value = it}, // Takes what user entered and sets to value state
        label = { Text(
            text = placeHolder,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = MaterialTheme.typography.labelMedium.fontSize,
            style = TextStyle(
                background = Color.Transparent
            )

        ) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
            ),
        keyboardActions = onAction,
        textStyle = TextStyle(
            //color = MaterialTheme.colorScheme.outline,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize

        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            cursorColor = Color.Black
        ),
        shape = MaterialTheme.shapes.medium

    )

}
