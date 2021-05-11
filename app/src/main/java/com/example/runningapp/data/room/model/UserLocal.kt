package com.example.runningapp.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    var id: String = "",
    var email: String = "Mock email",
    var name: String? = "Mock name"
)