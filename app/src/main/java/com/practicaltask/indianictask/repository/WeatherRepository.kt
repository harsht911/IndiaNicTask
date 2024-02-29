package com.practicaltask.indianictask.repository

import android.content.Context
import com.practicaltask.indianictask.api.ApiConstants.WEATHER_API_KEY
import com.practicaltask.indianictask.api.ApiInterface

import javax.inject.Inject

class WeatherRepository constructor(val context: Context, val apiService: ApiInterface) {

    fun getInstance(): WeatherRepository {
        return WeatherRepository(context,apiService)
    }

    suspend fun getWeatherInfo() =
        apiService.getWeatherDataAsync("23.051331", "72.531952", WEATHER_API_KEY)


}