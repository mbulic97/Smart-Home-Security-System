package com.example.smarthomesecuritysystemmirnes.pages

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthomesecuritysystemmirnes.R
import com.example.smarthomesecuritysystemmirnes.components.HeaderView
import com.example.smarthomesecuritysystemmirnes.components.SensorsView
import com.example.smarthomesecuritysystemmirnes.viewmodel.HomeViewModel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val hasInternet by viewModel.hasInternet.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val sensor by viewModel.sensor.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeInternet(connectivityManager)
        viewModel.loadUserName()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeaderView(userName = userName)
        Spacer(modifier = Modifier.height(10.dp))

        if (hasInternet) {
            SensorsView(sensor = sensor)
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Network error: unable to read sensor data :(",
                    fontSize = 32.sp,
                    style = TextStyle(lineHeight = 40.sp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.no_internet),
                    contentDescription = "No Internet",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }
    }
}
