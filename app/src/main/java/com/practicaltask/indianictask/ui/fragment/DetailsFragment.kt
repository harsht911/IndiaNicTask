package com.practicaltask.indianictask.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.practicaltask.indianictask.R
import com.practicaltask.indianictask.databinding.FragmentDetailsBinding
import com.practicaltask.indianictask.databinding.FragmentFutureForecastBinding


class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding?=null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getDouble("latitude", 0.0)
            longitude = it.getDouble("longitude", 0.0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_details,
            container,
            false
        )
        init()
        return binding!!.root
    }
    fun init(){
        binding!!.tvLatLong.text = "${latitude}, $longitude"
    }

    companion object {
        fun newInstance(latitude: Double, longitude: Double): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putDouble("latitude", latitude)
            args.putDouble("longitude", longitude)
            fragment.arguments = args
            return fragment
        }

    }
}