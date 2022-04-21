package com.example.weatherapp

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinate(
    @Json(name = "lat") var latitude: Double?,
    @Json(name = "lon") var longitude: Double?
) : Parcelable
