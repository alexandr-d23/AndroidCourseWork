package com.example.runningapp

import android.app.Application
import android.util.Log
import com.example.runningapp.di.components.AppComponent
import com.example.runningapp.di.components.DaggerAppComponent

class ApplicationDelegate : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MYTAG", "Application started")
        component = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var component: AppComponent
    }
}