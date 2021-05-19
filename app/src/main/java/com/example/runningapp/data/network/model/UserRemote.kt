package com.example.runningapp.data.network.model

import androidx.room.PrimaryKey

data class UserRemote(
    @PrimaryKey
    var id: String = "",
    var email: String = "",
    var name: String = ""
)