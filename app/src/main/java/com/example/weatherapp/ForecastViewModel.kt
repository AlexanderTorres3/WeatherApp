package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val api: Api) : ViewModel() {
    private val _dailyForecast:MutableLiveData<DailyForecast> = MutableLiveData()
    val dailyForecast: LiveData<DailyForecast>
        get() = _dailyForecast

    fun loadData() = runBlocking{
        launch { _dailyForecast.value = api.getDailyForecast("55055") }
    }
}