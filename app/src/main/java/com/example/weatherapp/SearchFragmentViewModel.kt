package com.example.weatherapp

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(private val api: Api) : ViewModel() {

    var zipCode: String? = null

    var coordinates = Coordinate(null, null)
    var latitude: Double? = null
    var longitude: Double? = null

    private val _currentConditions = MutableLiveData<CurrentConditions>()

    private val _enableButton = MutableLiveData(false)

    private val _showErrorDialog = MutableLiveData(false)

    private var notificaitonStatus = false

    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions

    val enableButton: LiveData<Boolean>
        get() = _enableButton

    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog

    fun updateZipCode(zipCode: String){
        if(zipCode != this.zipCode){
            this.zipCode = zipCode
            _enableButton.value = isValidZipCode(zipCode)
        }
    }

    fun updateLatLon(lat: Double, lon: Double){
        latitude = lat
        longitude = lon
        coordinates = Coordinate(lat, lon)
    }

    private fun isValidZipCode(zipCode: String): Boolean{
        return zipCode.length == 5 && zipCode.all { it.isDigit() }
    }

    fun loadDataZip() = runBlocking {
        launch{
            _currentConditions.value = api.getCurrentConditionsZip(zipCode!!)
            coordinates = currentConditions.value?.coordinate ?: Coordinate(null, null)
        }
    }

    fun loadDataLatLon() = runBlocking{
        launch{ _currentConditions.value = api.getCurrentConditionsLatLon(latitude, longitude)}
    }

    fun getNotificationStatus(): Boolean{
        return notificaitonStatus
    }

    fun setNotificationsToTrue(){
        notificaitonStatus = true
    }
    fun setNotificationsToFalse(){
        notificaitonStatus = false
    }


}