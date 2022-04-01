package com.example.weatherapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentConditions(
    val weather: List<WeatherConditions>,
    val main: Currents,
    val name: String
) : Parcelable