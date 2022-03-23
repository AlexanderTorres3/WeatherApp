package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(private val api: Api) : ViewModel() {

    var zipCode: String? = null

    private val _currentConditions = MutableLiveData<CurrentConditions>()

    private val _enableButton = MutableLiveData(false)

    private val _showErrorDialog = MutableLiveData(false)

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
    private fun isValidZipCode(zipCode: String): Boolean{
        return zipCode.length == 5 && zipCode.all { it.isDigit() }
    }

    fun loadData() = runBlocking {
        launch{ _currentConditions.value = api.getCurrentConditions(zipCode!!)}
    }
}