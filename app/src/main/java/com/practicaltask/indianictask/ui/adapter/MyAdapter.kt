package com.practicaltask.indianictask.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.practicaltask.indianictask.R
import com.practicaltask.indianictask.ui.fragment.DetailsFragment
import com.practicaltask.indianictask.ui.fragment.FutureForecastFragment

class MyAdapter(
    private val context: Context,
    private val fragmentActivity: FragmentActivity,
    private val totalTabs: Int,
    private val homeLatitude: Double,
    private val homeLongitude: Double

) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailsFragment.newInstance(homeLatitude, homeLongitude)
            1 -> FutureForecastFragment()
            else -> Fragment()
        }
    }
}
