package com.example.weatherapp

import com.squareup.moshi.Json

data class DailyForecast(
    @Json(name = "list") val forecastList: List<DayForecast>
)

