package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var currentWeatherApi: CurrentWeatherApi

    private lateinit var cityName: TextView
    private lateinit var  currentTemp: TextView
    private lateinit var  tempFeelsLike: TextView
    private lateinit var  tempLow: TextView
    private lateinit var  tempHigh: TextView
    private lateinit var pressure: TextView
    private lateinit var humidity: TextView
    private lateinit var conditionIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityName = findViewById(R.id.city_name)
        currentTemp = findViewById(R.id.temperature)
        tempFeelsLike = findViewById(R.id.feelsLike)
        tempLow = findViewById(R.id.low)
        tempHigh = findViewById(R.id.high)
        pressure = findViewById(R.id.pressure)
        humidity = findViewById(R.id.humidity)
       conditionIcon = findViewById(R.id.currentIcon)

        val moshi: Moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        currentWeatherApi = retrofit.create(CurrentWeatherApi::class.java)
    }

    override fun onResume() {
        super.onResume()
        val call: Call<CurrentConditions> = currentWeatherApi.getCurrentConditions("55055")
        call.enqueue(object : Callback<CurrentConditions> {
            override fun onResponse(call: Call<CurrentConditions>, response: Response<CurrentConditions>) {
                val currentConditions = response.body()
                currentConditions?.let {
                    bindData(it)
                }
            }

            override fun onFailure(call: Call<CurrentConditions>, t: Throwable) {
            }

        })
    }

    fun openForecast(view: android.view.View) {
        val intent = Intent(this, ForecastActivity::class.java)
        startActivity(intent)
    }

    private fun bindData(currentConditions: CurrentConditions){
        cityName.text = currentConditions.name
        currentTemp.text = getString(R.string.temperature, currentConditions.main.temp.toInt())
        tempFeelsLike.text = getString(R.string.feelsLikeDescription,
            currentConditions.main.feelsLike.toInt())
        tempLow.text = getString(R.string.low, currentConditions.main.minTemp.toInt())
        tempHigh.text = getString(R.string.high, currentConditions.main.maxTemp.toInt())
        pressure.text = getString(R.string.presure, currentConditions.main.pressure.toInt())
        humidity.text = getString(R.string.humidity, currentConditions.main.humidity.toInt())

        val iconName = currentConditions.weather.firstOrNull()?.icon
        val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
        Glide.with(this)
            .load(iconURL)
            .into(conditionIcon)


    }
}