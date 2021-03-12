package com.example.runningapp.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"]
    )
    ]
)
data class Sprint(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var userId: Int = 1,
    var seconds: Int = 0,
    var datetime: DateTime = DateTime()

)