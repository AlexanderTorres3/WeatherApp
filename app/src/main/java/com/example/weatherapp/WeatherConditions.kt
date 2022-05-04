package com.example.weatherapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherConditions(
    val main: String,
    val icon: String,
    val description: String
): Parcelable