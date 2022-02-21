package com.example.weatherapp

data class CurrentConditions(
    val weather: List<WeatherConditions>,
    val main: Currents,
    val name: String
)