package com.example.runningapp.domain.model

import androidx.room.PrimaryKey

data class User(
    @PrimaryKey
    var id: String? = null,
    var email: String? = null,
    var name: String? = null,
    var password: String? = null,
    var isSubscribed: Boolean? = null,
    var sprints: List<Sprint>? = null,
    var avgSpeed: Double = 0.0,
    var resultDistance: Long = 0,
    var timeInRun: Long = 0
)