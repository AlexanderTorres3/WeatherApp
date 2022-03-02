package com.example.weatherapp

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyForecastApi {
    @GET("forecast/daily")
    fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") unit: String = "imperial",
        @Query("appid") appId: String = "927421ea8053c52b201510fceed2dc23",
        @Query("cnt") count: Int = 16

    ): Call<DailyForecast>
}