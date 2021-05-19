package com.example.runningapp.data.network.api

import com.example.runningapp.data.network.model.Subscription
import com.example.runningapp.data.network.model.UserRemote
import com.example.runningapp.domain.model.User

interface FirebaseApi {
    suspend fun addUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun getSubscriptions(subscriberId: String): List<Subscription>
    suspend fun subscribe(subscription: Subscription)
    suspend fun unsubscribe(subscription: Subscription)
    suspend fun getUsers(): List<UserRemote>
}