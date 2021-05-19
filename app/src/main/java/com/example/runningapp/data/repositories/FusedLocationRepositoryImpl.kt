package com.example.runningapp.data.repositories

import android.annotation.SuppressLint
import android.os.Looper
import com.example.runningapp.domain.repositories.FusedLocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class FusedLocationRepositoryImpl(
    private val client: FusedLocationProviderClient
) : FusedLocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun requestLocationUpdates(
        request: LocationRequest,
        callback: LocationCallback,
        looper: Looper
    ) {
        client.requestLocationUpdates(request, callback, looper)
    }

    override suspend fun removeLocationUpdates(callback: LocationCallback) {
        client.removeLocationUpdates(callback)
    }

}
