package com.practicaltask.indianictask.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.practicaltask.indianictask.api.ApiInterface
import com.practicaltask.indianictask.api.RetrofitClient
import com.practicaltask.indianictask.repository.WeatherRepository


open class BaseViewModel(application: Application) : AndroidViewModel(application) {



    //REPOS
    var weatherRepository: WeatherRepository? = null

    fun getApi(): ApiInterface {
        return RetrofitClient.getRetrofitClientObj(getApplication()).getApiInterface()
    }

    fun init(context: Context) {
        this.weatherRepository = WeatherRepository(context, getApi())

    }

}