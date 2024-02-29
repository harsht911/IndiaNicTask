package com.practicaltask.indianictask.base

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi



class  MyApplication : Application(){

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    companion object {
        private val TAG = MyApplication::class.java.simpleName
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var instance: MyApplication? = null
            private set
    }
}