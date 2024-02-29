package com.practicaltask.indianictask.api

import com.practicaltask.indianictask.model.WeatherData
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {


    @GET("weather")
    suspend fun getWeatherDataAsync(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("apikey") apiKey: String
    ): Response<WeatherData>
}