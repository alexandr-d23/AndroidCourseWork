package com.example.runningapp.domain.model

import org.joda.time.DateTime

data class Sprint(
    var userId: String? = null,
    var secondsRun: Long = 0,
    var dateTime: DateTime = DateTime(),
    var avgSpeed: Double = 0.0,
    var distance: Long = 0
)