package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherApi {
    @GET("weather")
    fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") unit: String = "imperial",
        @Query("appid") appId: String = "84d6d4113c78c7e18f77a45441a687ed"
    ): Call<CurrentConditions>
}