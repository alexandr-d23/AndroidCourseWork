package com.example.runningapp.data.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.runningapp.data.room.model.SprintLocal
import com.example.runningapp.data.room.model.UserLocal


data class UserWithSprints(
    @Embedded
    var user: UserLocal,
    @Relation(entity = SprintLocal::class, parentColumn = "id", entityColumn = "userId")
    var list: List<SprintLocal>
)