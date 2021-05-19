package com.example.runningapp.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun calculateDistanceInMeters(list: List<LatLng>): Int{
    var distance = 0F
    val listLocation = list.map {
        Location(it.toString()).apply {
            latitude = it.latitude
            longitude = it.longitude
        }
    }
    for(i in 0 until listLocation.size - 1){
        val first = listLocation[i]
        val second = listLocation[i+1]
        distance += first.distanceTo(second)
    }
    return distance.toInt()
}