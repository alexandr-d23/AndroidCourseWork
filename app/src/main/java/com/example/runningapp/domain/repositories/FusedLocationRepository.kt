package com.example.runningapp.domain.repositories

import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

interface FusedLocationRepository {
    suspend fun requestLocationUpdates(
        request: LocationRequest,
        callback: LocationCallback,
        looper: Looper
    )

    suspend fun removeLocationUpdates(callback: LocationCallback)
}