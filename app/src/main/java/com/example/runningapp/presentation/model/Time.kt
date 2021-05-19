package com.example.runningapp.presentation.model

data class Time(
    private var secondsResult: Long = 0,
    var seconds: Int = 0,
    var minutes: Int = 0,
    var hours: Int = 0
) {
    override fun toString(): String {
        val stringHours = if (hours >= 10) "$hours" else "0$hours"
        val stringMinutes = if (minutes >= 10) "$minutes" else "0$minutes"
        val stringSeconds = if (seconds >= 10) "$seconds" else "0$seconds"
        return "$stringHours:$stringMinutes:$stringSeconds"
    }

    fun addSecond() {
        secondsResult++
        seconds++
        if (seconds == 60) {
            seconds = 0
            minutes++
            if (minutes == 60) {
                minutes = 0
                hours++
            }
        }
    }

    fun addSeconds(reqSeconds: Long){
        hours += (reqSeconds/3600).toInt()
        minutes += ((reqSeconds%3600)/60).toInt()
        seconds += (reqSeconds%60).toInt()
    }

    fun toSeconds(): Long = secondsResult
}