package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NotificationServiceViewModel @Inject() constructor(private val api: Api) : ViewModel()  {
    private val _currentConditions: MutableLiveData<CurrentConditions> = MutableLiveData()
    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions

    fun loadData(coordinate: Coordinate) = runBlocking{
        launch { _currentConditions.value = api.getCurrentConditionsLatLon(coordinate.latitude, coordinate.longitude) }
    }
}