package com.example.runningapp.domain.repositories

import androidx.lifecycle.LiveData
import com.example.runningapp.data.network.model.Subscription
import com.example.runningapp.data.room.relations.UserWithSprints
import com.example.runningapp.domain.model.User

interface UserRepository {
    fun getUsers(userId: String): LiveData<List<User>>
    fun getUserById(userId: String): LiveData<User?>
    suspend fun addUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun subscribe(subscription: Subscription)
    suspend fun unsubscribe(subscription: Subscription)
    suspend fun refreshUsers(userId: String)
}