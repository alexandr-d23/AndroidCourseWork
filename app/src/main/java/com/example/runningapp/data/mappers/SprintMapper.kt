package com.example.runningapp.data.mappers

import com.example.runningapp.data.network.model.SprintRemote
import com.example.runningapp.data.room.model.SprintLocal
import com.example.runningapp.domain.model.Sprint
import org.joda.time.DateTime

fun sprintToSprintRemote(sprint: Sprint): SprintRemote {
    if (sprint.userId == null) throw IllegalStateException("User id must be not null")
    return SprintRemote(
        userId = sprint.userId!!,
        distance = sprint.distance,
        dateTime = sprint.dateTime.millis,
        secondsRun = sprint.secondsRun,
        avgSpeedKilometerPerHour = sprint.avgSpeed
    )
}

fun sprintRemoteToSprint(sprint: SprintRemote): Sprint {
    return Sprint(
        userId = sprint.userId!!,
        distance = sprint.distance,
        dateTime = DateTime(sprint.dateTime),
        secondsRun = sprint.secondsRun,
        avgSpeed = sprint.avgSpeedKilometerPerHour
    )
}

fun sprintLocalToSprint(sprintLocal: SprintLocal): Sprint =
    Sprint(
        userId = sprintLocal.userId,
        secondsRun = sprintLocal.secondsRun,
        dateTime = sprintLocal.dateTime,
        avgSpeed = sprintLocal.avgSpeed,
        distance = sprintLocal.distance
    )
