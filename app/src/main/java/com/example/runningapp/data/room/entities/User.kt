package com.example.runningapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserAccount(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var email: String,
    var name: String,
    var password: String
)