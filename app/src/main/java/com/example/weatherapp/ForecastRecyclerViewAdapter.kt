package com.example.weatherapp

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ForecastDataBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastRecyclerViewAdapter(private val data: List<DayForecast>)
    : RecyclerView.Adapter<ForecastRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ForecastDataBinding) : RecyclerView.ViewHolder(binding.root) {

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

            binding.forecastDate.text =  dateFormatter.format(dateTime)
            binding.forecastTemp.text = itemView.context.getString(R.string.forecast_temp, info.temp.day)
            binding.forecastHigh.text = itemView.context.getString(R.string.forecast_high, info.temp.max)
            binding.forecastLow.text = itemView.context.getString(R.string.forecast_low, info.temp.min)
            binding.forecastSunrise.text = itemView.context.getString(R.string.forecast_sunrise,
                timeFormatter.format(sunriseTime))
            binding.forecastSunset.text = itemView.context.getString(R.string.forecast_sunset,
                timeFormatter.format(sunsetTime))

            val iconName = info.weather.firstOrNull()?.icon
            val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
            Glide.with(itemView)
                .load(iconURL)
                .into(binding.forecastImage)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ForecastDataBinding = ForecastDataBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}