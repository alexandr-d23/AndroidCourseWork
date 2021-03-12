package com.example.runningapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var email: String = "Mock email",
    var name: String = "Mock name",
    var password: String = "Mock password"
)