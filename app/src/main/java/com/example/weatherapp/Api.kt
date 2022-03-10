package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    suspend fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") unit: String = "imperial",
        @Query("appid") appId: String = "84d6d4113c78c7e18f77a45441a687ed"
    ): CurrentConditions

    @GET("forecast/daily")
    suspend fun getDailyForecast(
        @Query("zip") zip: String,
        @Query("units") unit: String = "imperial",
        @Query("appid") appId: String = "927421ea8053c52b201510fceed2dc23",
        @Query("cnt") count: Int = 16

    ): DailyForecast
}