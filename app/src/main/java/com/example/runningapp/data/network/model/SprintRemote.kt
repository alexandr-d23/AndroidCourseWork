package com.example.runningapp.data.network.model

import org.joda.time.DateTime

data class SprintRemote(
    var docId: String = "",
    var userId: String = "",
    var secondsRun: Long = 0,
    var dateTime: Long = 0,
    var avgSpeedKilometerPerHour: Double = 0.0,
    var distance: Long = 0
)