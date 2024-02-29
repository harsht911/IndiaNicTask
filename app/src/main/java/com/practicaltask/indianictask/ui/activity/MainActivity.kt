package com.practicaltask.indianictask.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.practicaltask.indianictask.R
import com.practicaltask.indianictask.base.BaseActivity
import com.practicaltask.indianictask.databinding.ActivityMainBinding
import com.practicaltask.indianictask.ui.adapter.MyAdapter
import com.practicaltask.indianictask.ui.viewmodel.VMWeatherList
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    var latitude:Double=0.0
    var longitude:Double=0.0
    private fun init() {
        if(intent.hasExtra("latitude")){
            latitude=intent.getDoubleExtra("latitude",0.0)
        }
        if(intent.hasExtra("longitude")){
            longitude=intent.getDoubleExtra("longitude",0.0)
        }
        binding!!.ivBack.setOnClickListener {
            finish()
        }
        binding!!.tabLayout.addTab(binding!!.tabLayout.newTab().setText("Today Details"))
        binding!!.tabLayout.addTab(binding!!.tabLayout.newTab().setText("5 Day-Forecast"))
        binding!!.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(this, this, binding!!.tabLayout.tabCount,latitude,longitude)
        binding!!.viewPager.adapter = adapter

        binding!!.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding!!.tabLayout.selectTab(binding!!.tabLayout.getTabAt(position))
            }
        })


        binding!!.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding!!.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // No implementation needed here
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // No implementation needed here
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()

    }
}