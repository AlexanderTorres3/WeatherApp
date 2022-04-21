package com.example.weatherapp

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentConditions(
    val weather: List<WeatherConditions>,
    val main: Currents,
    val name: String,
    @Json(name = "coord") val coordinate: Coordinate
) : Parcelable