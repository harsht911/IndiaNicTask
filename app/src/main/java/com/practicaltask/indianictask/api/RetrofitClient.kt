package com.practicaltask.indianictask.api

import android.content.Context

import com.google.gson.GsonBuilder


import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient private constructor(application: Context) {

    private lateinit var httpClient: OkHttpClient

    private lateinit var apiInterface: ApiInterface
    var gson = GsonBuilder()
        .setLenient()
        .create()

    init {
        buildHttpClient(application)
    }

    companion object {
        private lateinit var retrofitClient: RetrofitClient

        fun getRetrofitClientObj(application: Context): RetrofitClient {
            //  if (!Companion::retrofitClient.isInitialized) {
            retrofitClient = RetrofitClient(application)
            //   }
            return retrofitClient
        }
    }

    private fun buildHttpClient(application: Context) {
        val builder = OkHttpClient().newBuilder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val loggingInterceptor =
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        builder.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)
            val request: Request = requestBuilder.build()
            it.proceed(request)
        }
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.addInterceptor(loggingInterceptor)
        httpClient = builder.build()
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
    fun getApiInterface(): ApiInterface {
        if (!::apiInterface.isInitialized) {
            apiInterface = getRetrofit(ApiConstants.BASE_URL).create(ApiInterface::class.java)
        }
        return apiInterface
    }






}