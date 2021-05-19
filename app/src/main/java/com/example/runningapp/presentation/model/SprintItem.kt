package com.example.runningapp.presentation.model

import org.joda.time.DateTime

data class SprintItem(
    var userId: String? = null,
    var secondsRun: Time = Time(),
    var dateTime: DateTime = DateTime(),
    var avgSpeed: Double = 0.0,
    var distance: Long = 0
)