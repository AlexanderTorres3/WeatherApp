package com.example.weatherapp

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastRecyclerViewAdapter(private val data: List<DayForecast>)
    : RecyclerView.Adapter<ForecastRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val date: TextView = view.findViewById(R.id.forecastDate)
        private var temp: TextView = view.findViewById(R.id.forecastTemp)
        private var highTemp: TextView = view.findViewById(R.id.forecastHigh)
        private var lowTemp: TextView = view.findViewById(R.id.forecastLow)
        private var sunrise: TextView = view.findViewById(R.id.forecastSunrise)
        private var sunset: TextView = view.findViewById(R.id.forecastSunset)
        private var image: ImageView = view.findViewById(R.id.forecastImage)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(info: DayForecast){
            val dateInstant = Instant.ofEpochSecond(info.date)
            val dateTime = LocalDateTime.ofInstant(dateInstant, ZoneId.systemDefault())
            val sunriseInstant = Instant.ofEpochSecond(info.sunrise)
            val sunriseTime = LocalDateTime.ofInstant(sunriseInstant, ZoneId.systemDefault())
            val sunsetInstant = Instant.ofEpochSecond(info.sunset)
            val sunsetTime = LocalDateTime.ofInstant(sunsetInstant, ZoneId.systemDefault())
            val dateFormatter = DateTimeFormatter.ofPattern("MMM dd")
            val timeFormatter = DateTimeFormatter.ofPattern("h:mma")

            date.text = dateFormatter.format(dateTime)
            temp.text = itemView.context.getString(R.string.forecast_temp, info.temp.day)
            highTemp.text = itemView.context.getString(R.string.forecast_high, info.temp.max)
            lowTemp.text = itemView.context.getString(R.string.forecast_low, info.temp.min)
            sunrise.text =itemView.context.getString(R.string.forecast_sunrise,
                timeFormatter.format(sunriseTime))
            sunset.text = itemView.context.getString(R.string.forecast_sunset,
                timeFormatter.format(sunsetTime))

            val iconName = info.weather.firstOrNull()?.icon
            val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
            Glide.with(itemView)
                .load(iconURL)
                .into(image)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_data, parent, false)
        return  ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}