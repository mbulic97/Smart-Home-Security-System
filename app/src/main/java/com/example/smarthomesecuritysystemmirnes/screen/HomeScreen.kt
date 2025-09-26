package com.example.smarthomesecuritysystemmirnes.screen


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smarthomesecuritysystemmirnes.pages.HistoryPage
import com.example.smarthomesecuritysystemmirnes.pages.HomePage
import com.example.smarthomesecuritysystemmirnes.pages.ProfilePage


@Composable
fun HomeScreen(modifier: Modifier = Modifier,navController: NavController){
    val navItemList = listOf(
        NaviItem("Home", Icons.Default.Home),
        NaviItem("History", Icons.Default.DateRange),
        NaviItem("Profile", Icons.Default.AccountCircle),
    )
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                        },
                        label = {
                            Text(text = navItem.label)
                        })
                }
            }
        }
    ) {
        ContentScreen(modifier = modifier.padding(it),selectedIndex,navController)
    }
}
@Composable
fun ContentScreen(modifier: Modifier = Modifier,selectedIndex : Int,navController: NavController){
    when(selectedIndex){
        0-> HomePage(modifier)
        1-> HistoryPage(modifier)
        2-> ProfilePage(modifier,navController)
    }
}
@Preview(showBackground = true)
@Composable
fun HomeSrcenPreview(){
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}

data class NaviItem(
    val label : String,
    val icon : ImageVector
)