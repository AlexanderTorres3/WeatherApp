package com.example.weatherapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.FragmentCurrentConditionsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrentConditionsFragment : Fragment(R.layout.fragment_current_conditions) {
    private lateinit var binding: FragmentCurrentConditionsBinding
    @Inject lateinit var viewModel: CurrentConditionsFragmentViewModel
    private val args: CurrentConditionsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentConditionsBinding.bind(view)

        binding.currentConditionsButton.setOnClickListener {
            val lat = args.currentConditions.coordinate.latitude
            val lon = args.currentConditions.coordinate.longitude
            val coordinates = Coordinate(lat, lon)
            val action = CurrentConditionsFragmentDirections
                .actionCurrentConditionsFragmentToForecastFragment(coordinates)
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        bindData(args.currentConditions)
    }

    private fun bindData(currentConditions: CurrentConditions){
        binding.cityName.text = currentConditions.name
        binding.temperature.text = getString(R.string.temperature, currentConditions.main.temp.toInt())
        binding.feelsLike.text = getString(R.string.feelsLikeDescription,
            currentConditions.main.feelsLike.toInt())
        binding.low.text = getString(R.string.low, currentConditions.main.minTemp.toInt())
        binding.high.text = getString(R.string.high, currentConditions.main.maxTemp.toInt())
        binding.pressure.text = getString(R.string.presure, currentConditions.main.pressure.toInt())
        binding.humidity.text = getString(R.string.humidity, currentConditions.main.humidity.toInt())

        val iconName = currentConditions.weather.firstOrNull()?.icon
        val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
        Glide.with(this)
            .load(iconURL)
            .into(binding.currentIcon)
    }
}