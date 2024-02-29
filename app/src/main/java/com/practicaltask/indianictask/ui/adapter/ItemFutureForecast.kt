package com.practicaltask.indianictask.ui.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicaltask.indianictask.R
import com.practicaltask.indianictask.databinding.WeatherItemListBinding
import com.practicaltask.indianictask.model.Weather
import com.practicaltask.indianictask.model.WeatherData
import java.util.*


class ItemFutureForecast(
    val context: Context,
    val weatherData: MutableList<WeatherData>,

) :
    RecyclerView.Adapter<ItemFutureForecast.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemFutureForecast.ViewHolder {
        val binding: WeatherItemListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.weather_item_list, parent, false
        )
        return ViewHolder(binding)


    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: ItemFutureForecast.ViewHolder, position: Int) {



    }
    inner class ViewHolder(binding: WeatherItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: WeatherItemListBinding

        init {
            this.binding = binding


        }

    }


}