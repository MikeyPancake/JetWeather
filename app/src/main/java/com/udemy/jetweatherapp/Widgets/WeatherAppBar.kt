package com.udemy.jetweatherapp.Widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.udemy.jetweatherapp.Models.Favorite
import com.udemy.jetweatherapp.Navigation.WeatherScreens
import com.udemy.jetweatherapp.Screens.FavoriteScreen.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "City",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    // State holder for more dialog. Like a on/off switch
    val showDialog = remember{
        mutableStateOf(false)
    }

    if (showDialog.value){
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    // State for the toast
    val showIt = remember {
        mutableStateOf(false)
    }
    //  Defines the context
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .padding(start = 25.dp)
                    .shadow(elevation = elevation),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontStyle = MaterialTheme.typography.titleSmall.fontStyle,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                text = title)
                },
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .shadow(elevation = elevation),
            //.size(50.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer),
        actions = {
            // this is used to change icons based on the current screen
            if(isMainScreen){

                SearchButton(onAddActionClicked)

                MoreButton(showDialog)
            }
            else{
                Box {}
            }
        },
        navigationIcon = {
            // Here we will add either the favorites icon or back icon based on main screen
            if(icon != null){
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { onButtonClicked.invoke() },
                )
            }
            // This statement will add the city to the variable if the fav list contains the current city
            if (isMainScreen){
                val ifCityInFavList = favoriteViewModel.favList.collectAsState().value.filter{
                    item ->
                    (item.city == title.split(",")[0])

                }
                // If the variable does not contain a city, this means the current city is not on the fav list
                if(ifCityInFavList.isEmpty()){
                    // Display the fav list
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Icon",
                        tint = Color.Red.copy(alpha = 0.8f), // adjust "brightness" of shape
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                val dataList = title.split(",")
                                favoriteViewModel.insertFavorite(
                                    Favorite(
                                        city = dataList[0], // city name
                                        country = dataList[1] // country code
                                    )
                                )
                                    // This changes the toast state to true with displays a toast
                                    .run {
                                        showIt.value =true
                                    }
                            }
                    )
                }
                else{
                    Box{}
                    // sets toast state to false and toast will not be shown
                    showIt.value = false
                }
                ShowToast(context = context, showIt)

            }
        },
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value){
        Toast.makeText(context, "City Added to Favorites!", Toast.LENGTH_SHORT).show()
    }
}

@Composable
private fun MoreButton(showDialog: MutableState<Boolean>) {
    IconButton(
        onClick = {
            showDialog.value = true
        }
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "More Icons",
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun SearchButton(onAddActionClicked: () -> Unit) {
    IconButton(
        onClick = {
            // Passes the action for when the search icon is pressed
            onAddActionClicked.invoke()
        }
    ) {
        Icon(
            modifier = Modifier
                .size(25.dp),
            imageVector = Icons.Outlined.Search,
            contentDescription = "Search Icon",
            tint = MaterialTheme.colorScheme.onPrimaryContainer

        )
    }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {
    // State holder for the state of expanded
    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf("About", "Favorites", "Settings")

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            modifier = Modifier
                .width(150.dp)
                .background(Color.White),
            expanded = expanded ,
            onDismissRequest = { expanded = false}) {

            items.forEachIndexed { index, text ->

                DropdownMenuItem(text = {
                    Row() {
                        Icon(
                            imageVector = when(text) {
                                "About" -> Icons.Default.Info
                                "Favorites" -> Icons.Default.Favorite
                                else -> Icons.Default.Settings
                                                     },
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                        Text(
                            text,
                            modifier = Modifier
                                .padding(3.dp)
                                .clickable {
                                    navController.navigate(
                                        when (text) {
                                            "About" -> WeatherScreens.AboutScreen.name
                                            "Favorites" -> WeatherScreens.FavoritesScreen.name
                                            else -> WeatherScreens.SettingsScreen.name
                                        }
                                    )
                                },
                            fontWeight = FontWeight.W300,
                            color = MaterialTheme.colorScheme.scrim
                        )
                    }
                },
                    onClick = {
                        expanded = false
                        showDialog.value = false
                    }
                )

            }

        }

    }

}
