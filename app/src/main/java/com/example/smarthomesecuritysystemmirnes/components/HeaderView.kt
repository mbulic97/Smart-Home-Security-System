package com.example.smarthomesecuritysystemmirnes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HeaderView(userName: String) {
    Column {
        Text(text = "Welcome back")
        Text(
            text = userName,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
        )
    }
}