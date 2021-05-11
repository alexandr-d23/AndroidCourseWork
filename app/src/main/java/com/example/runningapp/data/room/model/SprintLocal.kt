package com.example.runningapp.data.room.model

import android.graphics.Bitmap
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
data class Sprint(
    @PrimaryKey(autoGenerate = true)
    var id: String? = null,
    var userId: String = "",
    var secondsRun: Int = 0,
    var dateTime: DateTime = DateTime(),
    var avgSpeed: Double = 0.0,
    var distance: Int = 0
)