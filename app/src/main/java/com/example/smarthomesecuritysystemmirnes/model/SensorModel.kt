package com.example.smarthomesecuritysystemmirnes.model

data class SensorModel(
    var arduinoId: String= "" ,
    var alarm: Boolean = false,
    var humidity: Int = 0,
    var pir: Boolean= false,
    var waterLevel: Boolean = false,
    var door: String = "",
    var temp: Int = 0
)
