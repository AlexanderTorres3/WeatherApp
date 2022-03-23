package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ForecastFragmentViewModel @Inject constructor(private val api: Api) : ViewModel() {
    private val _dailyForecast: MutableLiveData<DailyForecast> = MutableLiveData()
    val dailyForecast: LiveData<DailyForecast>
        get() = _dailyForecast

    fun loadData(zipCode: String) = runBlocking{
        launch { _dailyForecast.value = api.getDailyForecast(zipCode) }
    }
}