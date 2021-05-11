package com.example.runningapp.presentation.services

import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.runningapp.domain.usecases.repositories.FusedLocationRepository
import com.example.runningapp.presentation.notifications.RunNotification
import com.example.runningapp.utils.Constants
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class RunService : LifecycleService() {

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return localBinder
    }

    private lateinit var localBinder: LocalBinder
    private val isTracking = MutableLiveData<Boolean>(false)
    private val path = MutableLiveData<MutableList<LatLng>>(mutableListOf())

    private val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.IO

    @Inject
    lateinit var notification: RunNotification

    @Inject
    lateinit var fusedLocationRepository: FusedLocationRepository

    override fun onCreate() {
        super.onCreate()
        localBinder = LocalBinder()
        isTracking.observe(this) {
            updateLocationTracking(it)
        }
    }

    inner class LocalBinder() : Binder() {
        fun getService(): RunService = this@RunService
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            if (isTracking.value == true) {
                result?.locations?.let {
                    for (location in it) {
                        addPath(location)
                        Log.d(
                            "MYTAG", "RunService onLocationResult() : " +
                                    "new location ${location.latitude} ${location.longitude}"
                        )
                    }
                }
            }
        }
    }

    private fun updateLocationTracking(isTracking: Boolean) {
        if (isTracking) {
            val request = LocationRequest.create().apply {
                interval = Constants.LOCATION_INTERVAL
                fastestInterval = Constants.FASTEST_LOCATION_INTERVAL
                priority = PRIORITY_HIGH_ACCURACY
            }
            lifecycleScope.launch(coroutineContext) {
                fusedLocationRepository.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } else {
            lifecycleScope.launch(coroutineContext) {
                fusedLocationRepository.removeLocationUpdates(
                    locationCallback
                )
            }
        }
    }

    private fun addPath(location: Location?) {
        location?.let {
            val position = LatLng(location.latitude, location.longitude)
            path.value = path.value.also { list ->
                list?.add(position)
            }
        }
    }

    fun start() {
        startForeground(Constants.NOTIFICATION_ID, notification.getNotification())
        isTracking.value = true
        Log.d("MYTAG", "RunService start() : service started")
    }

    fun stop() {
        isTracking.value = false
        stopForeground(true)
        Log.d("MYTAG", "RunService stop: service stopped")
    }

    fun isTracking():LiveData<Boolean> = isTracking

    //TODO GENERICS
    fun  path(): LiveData<MutableList<LatLng>> = path
}