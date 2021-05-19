package com.example.runningapp.data.room.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(
    foreignKeys = [ForeignKey(
        entity = UserLocal::class,
        parentColumns = ["id"],
        childColumns = ["userId"]
    )
    ]
)
data class SprintLocal(
    @PrimaryKey
    var id: String = "",
    var userId: String = "",
    var secondsRun: Long = 0,
    var dateTime: DateTime = DateTime(),
    var avgSpeed: Double = 0.0,
    var distance: Long = 0
)