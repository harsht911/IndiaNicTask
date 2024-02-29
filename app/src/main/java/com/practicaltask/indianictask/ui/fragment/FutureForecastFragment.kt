package com.practicaltask.indianictask.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practicaltask.indianictask.R
import com.practicaltask.indianictask.databinding.FragmentFutureForecastBinding
import com.practicaltask.indianictask.ui.adapter.ItemFutureForecast


class FutureForecastFragment : Fragment() {
    var binding:FragmentFutureForecastBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
        R.layout.fragment_future_forecast,
            container,
            false
        )
        init()
        return binding!!.root
    }

    fun init() {
        val itemFutureForeCast: ItemFutureForecast =
            ItemFutureForecast(requireActivity(), mutableListOf())
        binding!!.rvFutureForeCast.adapter = itemFutureForeCast

    }

}