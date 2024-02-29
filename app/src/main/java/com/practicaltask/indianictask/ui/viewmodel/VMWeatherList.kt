package com.practicaltask.indianictask.ui.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicaltask.indianictask.base.BaseViewModel
import com.practicaltask.indianictask.model.WeatherData
import com.practicaltask.indianictask.repository.WeatherRepository

import kotlinx.coroutines.launch
import javax.inject.Inject


class VMWeatherList(
    application: Application
) : BaseViewModel(application) {

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData

    init {

        fetchWeather()
    }

    private fun fetchWeather() = viewModelScope.launch {
        weatherRepository!!.getWeatherInfo().let { response ->
            if (response.isSuccessful) {
                _weatherData.postValue(response.body())
            } else {
                Log.d("TAG>>>>", "fetchWeather: ${response.message()}")
            }
        }
    }
}