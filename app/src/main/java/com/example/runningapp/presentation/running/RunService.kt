package com.example.runningapp.presentation.running

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
import com.example.runningapp.ApplicationDelegate
import com.example.runningapp.domain.model.Sprint
import com.example.runningapp.domain.repositories.FusedLocationRepository
import com.example.runningapp.domain.usecases.RunUseCase
import com.example.runningapp.presentation.model.SprintItem
import com.example.runningapp.utils.Constants
import com.example.runningapp.utils.calculateDistanceInMeters
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.coroutines.CoroutineContext

class RunService : LifecycleService() {


    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return localBinder
    }

    private var localBinder: LocalBinder = LocalBinder()
    private val isTracking = MutableLiveData(false)
    private var path = MutableLiveData<MutableList<LatLng>>(mutableListOf())

    private var timer: Timer? = null
  //  private val time: MutableLiveData<Time?> = MutableLiveData()
    private val sprint: MutableLiveData<SprintItem?> = MutableLiveData()

    @Inject
    lateinit var runUseCase: RunUseCase

    @Inject
    @Named("IO")
    lateinit var coroutineContext: CoroutineContext

    @Inject
    lateinit var notification: RunNotification

    @Inject
    lateinit var fusedLocationRepository: FusedLocationRepository

    override fun onCreate() {
        super.onCreate()
        ApplicationDelegate.component.inject(this)
        localBinder = LocalBinder()
        isTracking.observe(this) {
            updateLocationTracking(it)
        }
    }

    inner class LocalBinder() : Binder() {
        fun getService(): RunService = this@RunService
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            if (isTracking.value == true) {
                result?.locations?.let {
                   addPathFromLocations(it)
                }
            }
        }
    }

    private fun addPathFromLocations(locations: List<Location>){
        for (location in locations) {
            addPath(location)
            Log.d(
                "MYTAG", "RunService onLocationResult() : " +
                        "new location ${location.latitude} ${location.longitude}"
            )
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
            path.value = path.value.also { list ->
                list?.add(LatLng(location.latitude, location.longitude))
            }
        }
    }

    fun start() {
        startForeground(Constants.NOTIFICATION_ID, notification.getNotification())
        isTracking.postValue(true)
        startTimer()
        Log.d("MYTAG", "RunService start() : service started")
    }

    private fun startTimer() {
        sprint.value = SprintItem()
        timer = Timer()
        path.postValue(mutableListOf())
        timer?.scheduleAtFixedRate(0, 1000) {
            sprint.postValue(sprint.value?.also {
                it.secondsRun.addSecond()
                it.distance = calculateDistanceInMeters(
                    path.value ?: throw IllegalStateException("path must be not null")
                ).toLong()
                Log.d("MYTAG", "RunService startTimer(): ${it.toString()}")
            })
        }
    }

    private fun stopTimer() {
        timer?.cancel()
    }

    fun stop() {
        stopTimer()
        isTracking.postValue(false)
        stopForeground(true)
        saveRun()
        clearSprint()
        Log.d("MYTAG", "RunService stop: service stopped")
    }

    private fun saveRun() {
        sprint.value?.let { item ->
            lifecycleScope.launch(coroutineContext) {
                runUseCase.saveSprint(item)
            }
        }

    }

    private fun clearSprint(){
        sprint.postValue(null)
    }

    fun isTracking(): LiveData<Boolean> = isTracking

    //TODO GENERICS
    fun path(): LiveData<MutableList<LatLng>> = path

    fun getSprint(): LiveData<SprintItem?> = sprint

}