package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ForecastActivity : AppCompatActivity() {

    lateinit var dailyForecastApi: DailyForecastApi
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val moshi: Moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        dailyForecastApi = retrofit.create(DailyForecastApi::class.java)
    }

    override fun onResume() {
        super.onResume()
        val call: Call<DailyForecast> = dailyForecastApi.getCurrentConditions("55055")
        call.enqueue(object : Callback<DailyForecast> {
            override fun onResponse(call: Call<DailyForecast>, response: Response<DailyForecast>) {
                val dailyForecast = response.body()
                dailyForecast?.let {
                    recyclerView.adapter = ForecastRecyclerViewAdapter(dailyForecast.forecastList)
                }
            }

            override fun onFailure(call: Call<DailyForecast>, t: Throwable) {
            }

        })
    }


}