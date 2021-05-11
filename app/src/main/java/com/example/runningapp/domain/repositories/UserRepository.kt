package com.example.runningapp.domain.usecases.repositories

import com.example.runningapp.data.room.entities.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun addUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun subscribe(subscriberId: String, subscribedId: String)
    suspend fun unsubscribe(subscriberId: String, subscribedId: String)
}