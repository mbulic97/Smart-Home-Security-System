package com.example.smarthomesecuritysystemmirnes.components

import android.content.Context

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp
import com.example.smarthomesecuritysystemmirnes.model.SensorModel

@Composable
fun SensorsView(sensor: SensorModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val vibrator = remember {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    LaunchedEffect(sensor.alarm, sensor.pir, sensor.waterLevel) {
        if (sensor.alarm || sensor.pir || sensor.waterLevel) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createWaveform(
                        longArrayOf(0, 500, 500), // pauza, vibracija, pauza
                        0 // 0 = loop, -1 = bez loop
                    )
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(500)
            }
        } else {
            vibrator.cancel()
        }
    }

    Column(modifier = modifier) {
        Text(
            text = "Arduino ID: ${sensor.arduinoId}",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
        )

        Text(
            "Alarm: ${if (sensor.alarm) "Active" else "Inactive"}",
            color = if (sensor.alarm) Color.Red else MaterialTheme.colorScheme.onBackground,
            fontSize = 22.sp
        )

        Text("Humidity: ${sensor.humidity}%", fontSize = 22.sp)

        Text(
            "PIR: ${if (sensor.pir) "Detected" else "Clear"}",
            color = if (sensor.pir) Color.Red else MaterialTheme.colorScheme.onBackground,
            fontSize = 22.sp
        )

        Text(
            "Water Level: ${if (sensor.waterLevel) "Flooding" else "Normal"}",
            color = if (sensor.waterLevel) Color.Red else MaterialTheme.colorScheme.onBackground,
            fontSize = 22.sp
        )

        Text("Door: ${sensor.door}", fontSize = 22.sp)

        Text("Temperature: ${sensor.temp} °C", fontSize = 22.sp)
    }
}
