package com.example.runningapp.data.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.runningapp.data.room.entities.Sprint
import com.example.runningapp.data.room.entities.User


data class UserWithSprints(
    @Embedded
    var user: User,
    @Relation(entity = Sprint::class, parentColumn = "id", entityColumn = "userId")
    var list: List<Sprint>
)